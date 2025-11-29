import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public static void main(String[] args) throws Exception {

    UploadServlet uploadServlet = new UploadServlet();

    Server server = new Server(3000);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");


    ServletHolder defaultHolder = new ServletHolder("default", new DefaultServlet());

    defaultHolder.setInitParameter("resourceBase", "static");
    defaultHolder.setInitParameter("dirAllowed", "false");

    context.addServlet(defaultHolder, "/");

    ServletHolder uploadHolder = new ServletHolder("upload", uploadServlet);
    uploadHolder.getRegistration().setMultipartConfig(
            new jakarta.servlet.MultipartConfigElement((String) null)
    );

    context.addServlet(uploadHolder, "/upload");

    server.setHandler(context);


    server.start();
    String ip = Network.getActiveLocalIp();
    System.out.println("Server started on: http://" + ip + ":" + "3000");
    server.join();
}


