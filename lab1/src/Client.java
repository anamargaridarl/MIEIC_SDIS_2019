import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.String;

public class Client {
    private static int port;
    private static InetAddress address;
    private static DatagramSocket socket = null;


    public static void main(String[] args) throws IOException {
        port = Integer.parseInt(args[1]);
        address = InetAddress.getByName(args[0]);
        socket = new DatagramSocket();

        sendRequest();
        processReply();
    }

    private static void processReply() {
        System.out.print("Hola");
    }

    private static void sendRequest() throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }

}
