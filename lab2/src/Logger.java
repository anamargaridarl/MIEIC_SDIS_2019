import java.net.InetAddress;
import java.util.List;

public class Logger {

    public static void logAdvertisement(InetAddress mcast_addr, int mcast_port, String server_addr, String server_port) {
        System.out.print("multicast: "+ mcast_addr.toString() + mcast_port + " : " + server_addr + server_port);
    }

    public static void logReply(List<String> operation, String[] result) {
        System.out.print("Client:");

        for (String op : operation) {
            System.out.print(" " + op);
        }

        System.out.print(" :");

        for (String r : result) {
            System.out.print(" " + r);
        }

        System.out.print("\n");
    }

    public static void logRequest(String[] args)
    {
        System.out.print("Server:");
        for(String arg: args) {
            System.out.print(" " + arg);
        }
        System.out.print("\n");
    }
}
