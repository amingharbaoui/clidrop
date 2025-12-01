package clidrop.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {

    private final Map<String, Path> sends = new ConcurrentHashMap<>();
    private Server server;
    private int port = 3000;
    private String ip;

    public void start() throws Exception {


        if (server != null && server.isStarted()) {
            return;
        }

        UploadServlet uploadServlet = new UploadServlet();
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        SendServlet sendServlet = new SendServlet(this);
        ServletHolder sendHolder = new ServletHolder("send", sendServlet);
        context.addServlet(sendHolder, "/send");


        String resourceBase = ServerManager.class.getClassLoader().getResource("").toExternalForm();

        ServletHolder defaultHolder = new ServletHolder("default", new DefaultServlet());
        defaultHolder.setInitParameter("resourceBase", resourceBase);
        defaultHolder.setInitParameter("dirAllowed", "false");

        context.addServlet(defaultHolder, "/");

        ServletHolder uploadHolder = new ServletHolder("upload", new UploadServlet());

        uploadHolder.getRegistration().setMultipartConfig(new jakarta.servlet.MultipartConfigElement((String) null));
        context.addServlet(uploadHolder, "/upload");

        server.setHandler(context);
        server.start();

        ip = Network.getActiveLocalIp();

    }

    public Map<String, Path> getSends() {
        return sends;
    }

    public String createSendUrlFor(Path target) throws UnsupportedEncodingException {
        String id = UUID.randomUUID().toString();
        sends.put(id, target);

        String baseUrl = getUrl();
        return baseUrl + "/send?id=" + URLEncoder.encode(id, StandardCharsets.UTF_8.name());
    }

    public void stop() throws Exception {
        if (server != null && server.isStarted()) {
            server.stop();
        }
    }

    public String getUrl() {
        return "http://" + (ip != null ? ip : Network.getActiveLocalIp()) + ":" + port;
    }

    public boolean isRunning() {
        return server != null && server.isStarted();
    }
}
