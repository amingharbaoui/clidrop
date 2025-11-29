import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRCode {
    public static void main(String[] args) throws WriterException, IOException {
        String ip = Network.getActiveLocalIp();
        int port = 3000;
        String data = "http://" + ip + ":" + port + "/index.html";


        String baseDir = System.getProperty("user.dir");
        Path qrPath = Paths.get(baseDir, "qrcode.png");

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 250
                , 250);

        MatrixToImageWriter.writeToPath(matrix, "png", qrPath);
    }
}


