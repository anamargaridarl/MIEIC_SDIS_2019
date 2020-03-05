import java.io.IOException;
import java.lang.String;

public class Server {
    public static void main(String[] args) throws IOException {
       new ServerThread(args[0]).start();
    }
}

