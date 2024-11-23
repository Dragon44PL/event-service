package io.github.personal.event.tickets.generator;

import io.github.personal.event.tickets.TicketException;
import io.github.personal.event.tickets.dto.TicketDto;
import io.github.personal.event.tickets.generator.dto.QrCode;
import io.github.personal.event.tickets.vo.TicketId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.ContentStreamProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
final class AmazonS3TicketProvider implements TicketGenerator, TicketProvider {

    private final S3Client s3Client;
    private final QrCodeGenerator qrCodeGenerator;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public void generate(TicketDto ticket) {
        try {
            final var code = qrCodeGenerator.generateCode(ticket);
            upload(ticket, code);
        } catch (Exception e) {
            log.error("Exception thrown when generating QR code", e);
            throw new TicketException("Unable to generate QR code");
        }
    }

    private void upload(TicketDto ticketDto, QrCode code) {
        final var request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(ticketDto.id().id().toString())
                .metadata(Map.of("userId", ticketDto.userId().toString()))
                .build();
        final RequestBody body = RequestBody
                .fromContentProvider(ContentStreamProvider.fromByteArray(code.data()), code.type());

        log.info("Created request : {}", request);
        s3Client.putObject(request, body);
    }

    @Override
    public QrCode getTicketData(TicketId ticketId) {
        try {
            final var request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(ticketId.id().toString())
                    .build();
            final var data = s3Client.getObject(request);
            final var response = data.response();
            return new QrCode(data.readAllBytes(), response.contentType());
        } catch (Exception e) {
            log.error("Exception thrown when retrieving QR code", e);
            throw new TicketException("Unable to retrieve QR code");
        }
    }
}
