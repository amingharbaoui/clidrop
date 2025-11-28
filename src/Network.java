import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.nio.channels.DatagramChannel;

public class Network {
    public static String getActiveLocalIp() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(Inet4Address.getByName("192.168.0.1"), 80);
            return socket.getLocalAddress().getHostAddress();

        } catch (Exception e) {
            e.printStackTrace();
            return "127.0.0.1";
        }
    }
}
