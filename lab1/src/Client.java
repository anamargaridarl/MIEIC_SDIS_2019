import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    private static int port;
    private static InetAddress host;
    private static DatagramSocket socket = null;
    private static DatagramPacket packet;
    private static byte[] buf;

    public static void main(String[] args) throws IOException {

        port = Integer.parseInt(args[1]);
        host = InetAddress.getByName(args[0]);

        socket = new DatagramSocket();

        List<String> operation = retrieveOperation(args);
        sendRequest(operation);
        processReply(operation);
    }

    private static List<String> retrieveOperation(String[] args) {
        List<String> op = new ArrayList<>();
        op.addAll(Arrays.asList(args).subList(2, args.length));
        return op;
    }

    private static void processRequest(List<String> operation) {

        StringBuilder aux = new StringBuilder();
        for(String op : operation) {
            aux.append(op).append(" ");
        }
        buf = aux.toString().getBytes();
    }

    private static void sendRequest(List<String> operation) throws IOException {

        processRequest(operation);
        packet = new DatagramPacket(buf, buf.length, host, port);
        socket.send(packet);
    }

    private static void processReply(List<String> operation) throws IOException {

        socket.receive(packet);
        buf = packet.getData();
        String[] result = new String(buf,0,packet.getLength()).split(" ");
        System.out.print("Client:");

        for(String op: operation)
        {
            System.out.print(" " +op);
        }

        System.out.print(":" );

        for(String r: result) {
            System.out.print(" " + r);
        }

        System.out.print("\n");
    }
}
