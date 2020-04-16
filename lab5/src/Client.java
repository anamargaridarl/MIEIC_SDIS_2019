import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    private static int port;
    private static InetAddress host;
    private static String request;
    private static String reply;
    private static SSLSocket socket;
    private static String[] cyphersuits;

    public void main(String[] args) throws IOException {
        port = Integer.parseInt(args[1]);
        host = InetAddress.getByName(args[0]);

        socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host,port);

        List<String> operation = retrieveOperation(args);
        retrieveCypherSuits(operation.get(2));
        authenticateServer();
        sendRequest(operation);
        processReply(operation);
    }

    private void retrieveCypherSuits(String csuits)
    {
        cyphersuits = csuits.split(" ");
    }

    private static List<String> retrieveOperation(String[] args) {
        List<String> op = new ArrayList<>();
        op.addAll(Arrays.asList(args).subList(2, args.length));
        return op;
    }

    private void authenticateServer() throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //TODO:process in case of no cyphersuits
        socket.setEnabledCipherSuites(cyphersuits);
        //TODO: get cyphersuits chosen by the server

    }

    private static void processRequest(List<String> operation) {

        StringBuilder aux = new StringBuilder();
        for(String op : operation) {
            aux.append(op).append(" ");
        }
        request = aux.toString();
    }

    private static void sendRequest(List<String> operation) throws IOException {

        processRequest(operation);
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        out.println(request);


    }

    private static void processReply(List<String> operation) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        reply = in.readLine();
        if(reply != null) {

            String[] result = reply.split(" ");
            System.out.print("Client:");

            for(String op: operation)
            {
                System.out.print(" " +op);
            }

            System.out.print(" :" );

            for(String r: result) {
                System.out.print(" " + r);
            }

            System.out.print("\n");
        } else {
            System.out.println("Failed to receive reply from server");
        }
    }

}
