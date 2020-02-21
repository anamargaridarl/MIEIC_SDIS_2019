import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.String;

public class Client {
    private static int port;
    private static InetAddress address;
    private static DatagramSocket socket = null;
    private static DatagramPacket packet;
    private static byte[] buf;

    public static void main(String[] args) throws IOException {
        port = Integer.parseInt(args[1]);
        address = InetAddress.getByName(args[0]);
        socket = new DatagramSocket();

        sendRequest();
        processReply(args);
    }

    private static void processReply(String[] operands) throws IOException {

            buf =  new byte[256];
            socket.receive(packet);
            buf = packet.getData();

            String[] result = new String(buf,buf.length).split(" ");
            System.out.print("Client: " );

            for(int i = 2; i < operands.length; i++) {
                System.out.print(" " + operands[i]);
            }

            System.out.print(":" );

            for(String r: result) {
                System.out.print(" " + r);
            }

            System.out.print("\n");

    }

    private static void sendRequest() throws IOException {
        buf =  new byte[256];
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }

}
