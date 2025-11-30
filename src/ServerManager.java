import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerManager {
    private Server server;
    private int port = 3000;
    private String ip;


    public void start() throws Exception {

        if (server != null && server.isStarted()) {
            return;
        }

        UploadServlet uploadServlet = new UploadServlet();
        server = new Server(port);

        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ServletHolder defaultHolder = new ServletHolder("default", new DefaultServlet());
        defaultHolder.setInitParameter("resourceBase", "static");
        defaultHolder.setInitParameter("dirAllowed", "false");

        context.addServlet(defaultHolder, "/");

        ServletHolder uploadHolder = new ServletHolder("upload", new UploadServlet());

        uploadHolder.getRegistration().setMultipartConfig(new jakarta.servlet.MultipartConfigElement((String) null));
        context.addServlet(uploadHolder, "/upload");

        server.setHandler(context);
        server.start();

        ip = Network.getActiveLocalIp();
        System.out.println("Server started on:  http//" + ip + ":" + port);
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    public String getUrl() {
        return "http://" + (ip != null ? ip : Network.getActiveLocalIp()) + ":" + port;
    }
}
