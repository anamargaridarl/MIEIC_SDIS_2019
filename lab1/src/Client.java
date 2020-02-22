import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.String;
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

        List<String> operands = Arrays.asList(args[3].split(","));
        sendRequest(args[2],operands);
        processReply(args[2],operands);
    }


    private static void processRequest(String operation, List<String> operands) {

        String aux = operation;

        for(String operand: operands)
        {
            aux += " " + operand;
        }

        buf = aux.getBytes();
        
    }

    private static void sendRequest(String operation, List<String> operands) throws IOException {

        processRequest(operation,operands);
        packet = new DatagramPacket(buf, buf.length, host, port);
        socket.send(packet);
    }

    private static void processReply(String arg, List<String> operands) throws IOException {

        buf =  new byte[256];

        socket.receive(packet);
        System.out.print("Received "+ packet);
        buf = packet.getData();

        String[] result = new String(buf,buf.length).split(" ");
        System.out.print("Client: " + arg );

        for(String op: operands)
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
