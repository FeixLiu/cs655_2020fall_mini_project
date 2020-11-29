import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public int port;
    public static boolean available = true;
    public Server(int port) {
        this.port = port;
    }
    public void init() {
        try {
            System.out.println("Server started!");
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket client = serverSocket.accept(); // connect successfully
                available = false;
                System.out.println("Server's: " + available);
                new ServerThread(client);
                System.out.println("Server's: " + available);
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

        Server server = new Server(portNumber);
        server.init();
    }
}