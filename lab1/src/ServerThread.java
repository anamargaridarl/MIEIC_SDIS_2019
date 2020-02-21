import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class ServerThread {
    DatagramSocket socket;
    Map<String,String> dns_db;
    ServerThread(String port) throws IOException {
        socket = new DatagramSocket(Integer.parseInt(port));
        dns_db = new HashMap<String, String>();
    }

    void start() throws IOException {
        byte[] data = new byte[256];
        DatagramPacket packet;
        while(true) {
            packet = new DatagramPacket(data,data.length);
            socket.receive(packet);
            data =  processPacket(packet).getBytes();
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

        if(args[0].equalsIgnoreCase("REGISTER")) {
            try {
                int value = registerAddress(args[1],args[2]);
                return Integer.toString(value);
            } catch (AlreadyRegistered alreadyRegistered) {
                return response;
            }
        }

        else if(args[0].equalsIgnoreCase("LOOKUP")) {
            try {
                return lookupAddress(args[1]);
            } catch (NoAddress noAddress) {
                return response;
            }
        }

        return response;
    }

    private void logRequest(String[] args) {
        System.out.print("Server: " + args[0].toLowerCase());
        for(String arg: args) {
            System.out.print(" " + arg);
        }
        System.out.print("\n");
    }


    private int registerAddress(String dns, String ip) throws AlreadyRegistered {
        if(dns_db.containsKey(dns))
            throw new AlreadyRegistered();
        else {
            dns_db.put(dns, ip);
            return dns_db.size();
        }
    }

    private String lookupAddress(String dns) throws NoAddress {

        String response;

        if(dns_db.containsKey(dns)) {
            response = dns + dns_db.get(dns);
        }
        else
            throw new NoAddress();

        return response;

    }
}
