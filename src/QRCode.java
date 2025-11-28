import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;

public class QRCode {
    public static void main(String[] args) throws WriterException, IOException {
        String data = "http://10.1.89.199:3000";
        String path = "C:/Users/Amin/Documents/clidrop/qrcode.jpg";

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500
                , 500);

        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
    }
}


