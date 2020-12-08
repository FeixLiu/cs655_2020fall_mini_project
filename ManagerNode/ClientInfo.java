import java.net.Socket;

public class ClientInfo {
    // save the clint's info such as the connection socket
    // the connection request
    // and how many time it waits in the queue

    public Socket client;
    public String message;
    public long enqueueTime;
}
