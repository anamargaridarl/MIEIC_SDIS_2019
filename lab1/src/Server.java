import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.lang.String;

public class Server {
    public static void main(String[] args) throws IOException {
       new ServerThread(args[0]).start();
    }
}

