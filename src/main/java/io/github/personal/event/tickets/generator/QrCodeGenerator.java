package io.github.personal.event.tickets.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import io.github.personal.event.tickets.dto.TicketDto;
import io.github.personal.event.tickets.generator.dto.QrCode;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
class QrCodeGenerator {

    private static final String FILE_TYPE = "png";

    private final ObjectMapper objectMapper = new ObjectMapper();

    QrCode generateCode(TicketDto ticket) throws IOException, WriterException {
        final var code = objectMapper.writer().writeValueAsString(ticket);
        final var codeWriter = new QRCodeWriter();
        final var bitMatrix = codeWriter.encode(code, BarcodeFormat.QR_CODE, 300, 300);
        final var image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        final var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, FILE_TYPE, byteArrayOutputStream);
        return new QrCode(byteArrayOutputStream.toByteArray(), FILE_TYPE);
    }
}
