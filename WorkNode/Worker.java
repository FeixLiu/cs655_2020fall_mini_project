import java.util.*;
import java.io.*;
import java.net.*;

public class Worker {
    private int portNumber;

    public Worker(int port) {
        this.portNumber = port;
    }

    public void init(){
        System.out.println("The worker is working on port: " + portNumber);
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String message = br.readLine();
                System.out.println("Receive message: " + message);  // receive a request form the manager
                String[] messages = message.split(",");
                String key = messages[0].split(":")[1]; // the md5 string
                String ip = messages[1].split(":")[1];  // the return ip address (manager's IP)
                int port = Integer.parseInt(messages[2].split(":")[1]); // the return port number (manager's port)

                inputStream.close();
                String[] arr = {"aaaaa", "ZZZZZ"};  // the range the worker has to work on
                List<String[]> list = new ArrayList<>();
                list.add(arr);
                String res = Cracker.findPassword(list, key);   // get the result
                System.out.println("The result for the request: " + key + " is: " + res);

                Socket rstSender = new Socket(ip, port);
                OutputStream outputStream = rstSender.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
                bw.write(res);  // return the result to the manager
                bw.flush();
                outputStream.close();
                socket.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        Worker worker = new Worker(portNumber);
        worker.init();
    }
}
