import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

public class Client {
    //Multicast group
    private static int mcast_port;
    private static InetAddress mcast_addr;
    private static MulticastSocket a_socket = null;
    //Server DNS service
    private static InetAddress server_addr;
    private static int server_port;
    private static DatagramSocket s_socket = null;
    private static DatagramPacket packet_req;
    private static DatagramPacket packet_res;

    private static byte[] buf;

    public static void main(String[] args) throws IOException {
        mcast_addr = InetAddress.getByName(args[0]);
        mcast_port = Integer.parseInt(args[1]);

        s_socket = new DatagramSocket();
        a_socket = new MulticastSocket(mcast_port);

        retrieveServiceLocation();

        List<String> operation = retrieveOperation(args);
        sendRequest(operation);
        processReply(operation);
    }

    private static void retrieveServiceLocation() throws IOException {
        a_socket.joinGroup(mcast_addr);
        buf = new byte[256];
        packet_res = new DatagramPacket(buf,buf.length);

        a_socket.setSoTimeout(10000);
        try {
            a_socket.receive(packet_res);
        } catch (SocketTimeoutException e) {
            System.out.print("Timeout retrieving information in multicast channel! \n");
            exit(1);
        }

        processAdvertisement();

    }

    private static void processAdvertisement() throws IOException {
        buf = new byte[256];
        buf = packet_res.getData();
        String[] result = new String(buf,0,packet_res.getLength()).split(" ");
        Logger.logAdvertisement(mcast_addr, mcast_port,result[0],result[1]);
        server_addr = InetAddress.getByName(result[0]);
        server_port = Integer.parseInt(result[1]);
        a_socket.leaveGroup(mcast_addr);

    }

    private static List<String> retrieveOperation(String[] args) {
        List<String> op = new ArrayList<>();
        op.addAll(Arrays.asList(args).subList(2, args.length));
        return op;
    }

    private static void processRequest(List<String> operation) {
        buf = new byte[256];
        StringBuilder aux = new StringBuilder();
        for(String op : operation) {
            aux.append(op).append(" ");
        }
        buf = aux.toString().getBytes();
    }

    private static void sendRequest(List<String> operation) throws IOException {
        processRequest(operation);
        packet_req = new DatagramPacket(buf,buf.length, server_addr, server_port);

        s_socket.setSoTimeout(10000);
        try {
            s_socket.send(packet_req);
        } catch (SocketTimeoutException e) {
            System.out.print("Timeout! Couldn't send request to server \n");
            exit(1);
        }

    }

    private static void processReply(List<String> operation) throws IOException {
        buf = new byte[256];
        packet_res = new DatagramPacket(buf,buf.length);

        s_socket.setSoTimeout(5000);
        try {
            s_socket.receive(packet_res);
        } catch (SocketTimeoutException e) {
            System.out.print("Timeout processing reply \n");
            exit(1);
        }

        buf = packet_res.getData();
        String[] result = new String(buf,0,packet_res.getLength()).split(" ");
        Logger.logReply(operation,result);
    }
}
