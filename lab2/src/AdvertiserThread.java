import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

class AdvertiserThread implements Runnable {

    InetAddress mcast_addr;
    int mcast_port;
    MulticastSocket socket = null;
    Timer t;
    TimerTask task;
    DatagramPacket packet;
    private static byte[] buf;
    String advertise;

    int server_port;
    InetAddress server_addr;

    AdvertiserThread(String mca, String mcp,String sp) throws IOException {
        mcast_addr = InetAddress.getByName(mca);
        mcast_port = Integer.parseInt(mcp);
        server_port = Integer.parseInt(sp);
        socket = new MulticastSocket(mcast_port);
        server_addr = InetAddress.getLocalHost();
        advertise = server_addr.toString() + " " + server_port;

        task = new TimerTask() {
            @Override
            public void run() {
                buf = new byte[256];
                buf = advertise.getBytes();
                packet = new DatagramPacket(buf, buf.length,mcast_addr,mcast_port);

                try {
                    socket.send(packet);
                    Logger.logAdvertisement(mcast_addr, mcast_port, server_addr.toString(),Integer.toString(server_port));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        };
    }

    @Override
    public void run() {
        t = new Timer();
        t.schedule(task,0,1000);

    }
}