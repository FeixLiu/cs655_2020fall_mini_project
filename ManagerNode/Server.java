import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    public int port;
    public static AtomicBoolean[] avail;
    public static BlockingQueue<ClientInfo> clientQueue = new LinkedBlockingQueue<>();

    public Server(int port) {
        avail = new AtomicBoolean[Config.NUM_OF_WORKER];
        for(int i = 0; i < avail.length; i++) {
            avail[i] = new AtomicBoolean(true);
        }
        this.port = port;
    }

    public void init() {
        try {
            System.out.println("Server started on port: " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            new QueueThread();
            while (true) {
                Socket client = serverSocket.accept(); // connect successfully
                InputStream inputStream = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String message = br.readLine();
                if (message.contains("connection-check")) {
                    System.out.println("Received a connection check request.");
                    String response = "";
                    response += "HTTP/1.1 200 OK\n";
                    response += "Access-Control-Allow-Origin:*\n";
                    response += "Content-Type: text\\plain\n";
                    response += "Content-Length: " + "OK".length() + '\n';
                    response += "\n";
                    response += "OK";

                    OutputStream outputStream = client.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bw.write(response);
                    bw.flush();
                    client.close();
                } else {
                    ClientInfo clientInfo = new ClientInfo();
                    clientInfo.message = message;
                    clientInfo.client = client;
                    clientQueue.add(clientInfo);
                }
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