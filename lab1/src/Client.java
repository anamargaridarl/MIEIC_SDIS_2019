import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.String;

public class Client {
    private int port;
    private InetAddress address;
    DatagramSocket socket = null;
    DatagramPacket packet;
    byte[] sendBuf = new byte[256];

    public static void main(String[] args) throws IOException {
        port = Integer.parseInt(args[1]);


    }

}
