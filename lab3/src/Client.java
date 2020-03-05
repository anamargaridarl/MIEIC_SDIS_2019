import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    private static String host;
    private static ServerInterface stub;

    private Client() {}

    public static void main(String[] args) throws IOException {

            String host = args[0];
            String remoteObject = args[1];
            List<String> operation = retrieveOperation(args);
            try {
                Registry registry = LocateRegistry.getRegistry(host);
                stub = (ServerInterface) registry.lookup(remoteObject);
                processRequest(operation);
            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            } catch (AlreadyRegistered | NoAddress alreadyRegistered) {
                alreadyRegistered.printStackTrace();
            }
    }

    private static void processRequest(List<String> operands) throws AlreadyRegistered, NoAddress, RemoteException {
        if(operands.get(0).equalsIgnoreCase("REGISTER")) {
            stub.registerAddress(operands.get(1),operands.get(2));
        } else if(operands.get(0).equalsIgnoreCase("LOOKUP")) {
            stub.lookupAddress(operands.get(1));
        }

    }

    private static List<String> retrieveOperation(String[] args) {
        List<String> op = new ArrayList<>();
        op.addAll(Arrays.asList(args).subList(2, args.length));
        return op;
    }
}
