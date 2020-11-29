import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Server {
    public int port;
    public static boolean[] avail = new boolean[Config.NUM_OF_WORKER];
    public Queue<Socket> clientQueue = new LinkedList<>();

    public Server(int port) {
        for(int i = 0; i < avail.length; i++) {
            avail[i] = true;
        }
        this.port = port;
    }
    public void init() {
        try {
            System.out.println("Server started!");
            System.out.println(port);
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("begin");
                Socket client = serverSocket.accept(); // connect successfully
                System.out.println("successfully connected");
                clientQueue.add(client);
                System.out.println("size: " + clientQueue.size());
                int availIndex = getAvail();
                System.out.println("index: " + availIndex);
                if(availIndex != -1) {
                    avail[availIndex] = false;
                    if(!clientQueue.isEmpty()) {
                        System.out.println("start a server thread");
                        new ServerThread(clientQueue.poll(), Config.workerMap.get(availIndex), availIndex);
                    }
                }
                else{
                    System.out.println("No worker available now.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  if has avail, return avail index, otherwise, return -1.
    public static int getAvail(){
        for(int i = 0; i < avail.length; i++) {
            if(avail[i]) return i;
        }
        return -1;
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