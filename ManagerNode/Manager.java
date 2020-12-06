import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Manager {
    public int port;
    public static AtomicBoolean[] avail;
    public static BlockingQueue<ClientInfo> clientQueue = new LinkedBlockingQueue<>();

    public Manager(int port) {
        avail = new AtomicBoolean[Config.NUM_OF_WORKER];
        for(int i = 0; i < avail.length; i++) {
            avail[i] = new AtomicBoolean(true);
        }
        this.port = port;
    }

    public void init() {
        try {
            System.out.println("Server started on port: " + port);
            ServerSocket serverSocket = new ServerSocket(port, 100);
            new QueueThread();
            while (true) {
                Socket client = serverSocket.accept(); // connect successfully
                System.out.println(client.toString());
                InputStream inputStream = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String message = br.readLine();
                if (message == null) {
                    System.out.println("Received an invalid request.");
                    String response = "";
                    OutputStream outputStream = client.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bw.write(response);
                    bw.flush();
                    client.close();
                }
                else if (message.contains("connection-check")) {
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
                    clientInfo.enqueueTime = System.currentTimeMillis();
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

        Manager server = new Manager(portNumber);
        server.init();
    }
}