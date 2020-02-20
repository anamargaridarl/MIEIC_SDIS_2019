import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.lang.String;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ServerThread {
    DatagramSocket socket;
    Map<InetAddress,String> dns_db;
    ServerThread(String port) throws IOException {
        socket = new DatagramSocket(Integer.parseInt(port));
        dns_db = new HashMap<InetAddress, String>();
    }

    void start() throws IOException {
        byte[] data = new byte[256];
        DatagramPacket packet;
        while(1) {
            packet = new DatagramPacket(data,data.length);
            socket.receive(packet);
            data = processPacket(packet).getBytes();
            reply(packet,data);

        }
    }

    private void reply(DatagramPacket packet, byte[] data) {
    }

    private String processPacket(DatagramPacket packet) {
        byte[] data = packet.getData();
        String[] args = new String(data,data.length).split(" ");
        String response = "-1";

        logRequest(args);
        /*if(args[0].equalsIgnoreCase("REGISTER"))
            response = registerAddress(args[1],args[2]);
        else if(args[0].equalsIgnoreCase("LOOKUP"))
            response = lookupAddress(args[1]);*/

        return response;
    }

    private void logRequest(String[] args) {
        System.out.print("Server: " + args[0].toLowerCase());
        for(String arg: args) {
            System.out.print(" " + arg);
        }
        System.out.print("\n");
    }

    private String registerAddress(String arg, String arg1) {
        InetAddress addr
    }

    private String lookupAddress(String arg) {
    }
}
