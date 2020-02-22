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
        sendRequest(operands);
        processReply(args);
    }

    private static void processRequest(List<String> operands) {

        String aux = "";

        for(String operand: operands)
        {
            aux += operand;
        }

        buf = aux.getBytes();
        
    }

    private static void sendRequest(List<String> operands) throws IOException {

        processRequest(operands);
        packet = new DatagramPacket(buf, buf.length, host, port);
        socket.send(packet);
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



}
