import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements ServerInterface {

    private static HashMap<String, String> dns_db;

    public static void main(String[] args)  {
        dns_db = new HashMap<String, String>();
        String remoteObject = args[0];
        try {
            Server obj = new Server();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 8080);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(remoteObject, stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public int registerAddress(String dns, String ip) throws AlreadyRegistered {

        if(dns_db.containsKey(dns))
            throw new AlreadyRegistered();
        else {
            dns_db.put(dns, ip);
            /* Logger.logReply(operation, );*/
            System.out.print("Registered: " + dns +  " " + ip);
            return dns_db.size();
        }
}

    public String lookupAddress(String dns) throws NoAddress {

        String response;

        if(dns_db.containsKey(dns)) {
            response = dns + " " + dns_db.get(dns);
        }
        else
            throw new NoAddress();

        /* Logger.logReply(operation, );*/
        System.out.print("Lookup: " + dns +  " " +response);

        return response;

    }




}

