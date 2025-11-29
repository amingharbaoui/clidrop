import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

public static void main(String[] args) throws Exception {

    Server server = new Server(3000);
    ServletContextHandler handler = new ServletContextHandler();
    ServletHolder holder = new ServletHolder(new DefaultServlet());

    holder.setInitParameter("resourceBase", "static");
    holder.setInitParameter("dirAllowed", "false");

    handler.addServlet(holder, "/*");


    handler.setContextPath("/");
    server.setHandler(handler);

    server.start();
    server.join();
}


