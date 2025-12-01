package clidrop.app;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QrCodeGenerator {

    public static Path generateForUrl(String url) throws WriterException, IOException {
        String baseDir = System.getProperty("user.dir");
        Path qrPath = Paths.get(baseDir, "sharing", "qrcode", "qrcode.png");

        Files.createDirectories(qrPath.getParent());

        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE
                , 500, 500);

        MatrixToImageWriter.writeToPath(matrix, "png", qrPath);
        return qrPath;
    }

    public static void main(String[] args) throws Exception {
        String ip = Network.getActiveLocalIp();
        int port = 3000;
        String url = "http://" + ip + ":" + port;
        Path path = QrCodeGenerator.generateForUrl(url);
    }

}


