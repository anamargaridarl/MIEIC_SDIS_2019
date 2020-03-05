import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    int registerAddress(String dns, String ip) throws AlreadyRegistered,RemoteException;
    String lookupAddress(String dns) throws NoAddress,RemoteException;
}
