package clidrop.app;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SendServlet extends HttpServlet {
    private final ServerManager manager;

    public SendServlet(ServerManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String id = req.getParameter("id");

            if (id == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");

                return;
            }

            Path file = manager.getSends().get(id);
            if (file == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                return;
            }


            String fileName = file.getFileName().toString();
            resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            resp.setContentType(Files.probeContentType(file));
            resp.setContentLengthLong(Files.size(file));

            try (InputStream in = Files.newInputStream(file);
                 OutputStream out = resp.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
