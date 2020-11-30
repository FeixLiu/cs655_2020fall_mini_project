import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    public int port;
    public static AtomicBoolean[] avail;
    public static BlockingQueue<Socket> clientQueue = new LinkedBlockingQueue<>();

    public Server(int port) {
        avail = new AtomicBoolean[Config.NUM_OF_WORKER];
        for(int i = 0; i < avail.length; i++) {
            avail[i].set(true);
        }
        this.port = port;
    }
    public void init() {
        try {
            System.out.println("Server started!");
            System.out.println(port);
            ServerSocket serverSocket = new ServerSocket(port);
            new QueueThread();
            while (true) {
                Socket client = serverSocket.accept(); // connect successfully
                clientQueue.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing required params.");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        if (portNumber < 58000 || portNumber > 58999) {
            System.out.println("Port number is invalid.");
            return;
        }

        String configFilename = args[1];
        Config.init(configFilename);

        Server server = new Server(portNumber);
        server.init();
    }
}