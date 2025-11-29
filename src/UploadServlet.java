import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    private final Mode mode;

    public UploadServlet(Mode mode) {
        this.mode = mode;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String homeDir = System.getProperty("user.home");
        Path targetDir = Paths.get(homeDir, "Downloads");

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(targetDir.toFile());
        }

        Files.createDirectories(targetDir);

        Part filePart = req.getPart("file");

        if (filePart == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int count = 0;

        for (Part p : req.getParts()) {

            if (!"file".equals(p.getName())) {
                continue;
            }

            String fileName = Paths.get(p.getSubmittedFileName()).getFileName().toString();
            Path savedPath = targetDir.resolve(fileName);
            p.write(savedPath.toString());
            count++;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
