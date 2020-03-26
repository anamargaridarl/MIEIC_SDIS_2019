import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class ServerThread {
    DatagramSocket socket;
    Map<String,String> dns_db;
    DatagramPacket packet_res;
    DatagramPacket packet_req;
    int serverPort;

    ServerThread(String port) throws IOException {
        serverPort = Integer.parseInt(port);
        socket = new DatagramSocket(serverPort);
        dns_db = new HashMap<String, String>();
    }

    void start() throws IOException {

        while(true) {
            byte[] buf = new byte[256];
            packet_req = new DatagramPacket(buf, buf.length);
            socket.receive(packet_req);
            buf = new byte[256];
            buf = processPacket(packet_req).getBytes();
            reply(buf);
        }
    }

    private String processPacket(DatagramPacket packet) {
        String[] args = new String(packet.getData(),0,packet.getLength()).split(" ");
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
        System.out.print("Server:");
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
            response = dns + " " + dns_db.get(dns);
        }
        else
            throw new NoAddress();

        return response;

    }

    private void reply(byte[] response) throws IOException {
        packet_res = new DatagramPacket(response,response.length,packet_req.getAddress(),packet_req.getPort());
        System.out.println("Lookup: " + new String(response,0,packet_res.getLength()));
        socket.send(packet_res);
    }

}
