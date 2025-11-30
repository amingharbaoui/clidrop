package clidrop.app;

import java.net.DatagramSocket;
import java.net.Inet4Address;

public class Network {

    private static String HOST = "8.8.8.8";
    private static int PORT = 3000;

    public static String getActiveLocalIp() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(Inet4Address.getByName(HOST), PORT);
            return socket.getLocalAddress().getHostAddress();

        } catch (Exception e) {
            e.printStackTrace();
            return "127.0.0.1";
        }
    }
}
