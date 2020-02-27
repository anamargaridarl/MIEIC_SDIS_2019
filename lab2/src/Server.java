import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
       Thread ServerT = new Thread(new ServerThread(args[0]));
       ServerT.start();
       Thread AdvertiseT = new Thread(new AdvertiserThread(args[1],args[2],args[0]));
       AdvertiseT.start();
    }
}

