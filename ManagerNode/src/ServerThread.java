import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class ServerThread implements Runnable{
    private Socket socket;
    public ServerThread(Socket client) {
        this.socket = client;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            // read the message sent from the client
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String message = br.readLine();

            message = message.split(" ")[1];
            String key = message.substring(6, 38);
            int id = Integer.parseInt(message.substring(42));
            System.out.println("Received a request: " + key);

            String rst = getResult(key, "10.10.1.2", "10.10.1.1");

            String response = "";
            response += "HTTP/1.1 200 OK\n";
            response += "Access-Control-Allow-Origin:*\n";
            response += "Content-Type: text\\plain\n";
            response += "Content-Length: " + rst.length() + '\n';
            response += "\n";
            response += rst;

            // response an "OK" message to the client
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(response);
            bw.flush();

            inputStream.close();
            outputStream.close();
            System.out.println("ST's: " + Server.available);
            Server.available = true;
            System.out.println("ST's: " + Server.available);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    e.printStackTrace();
                }
            }
        }
    }

    public String getResult(String key, String targetIp, String selfIp) {
        try {
            // assign the task to a worker
            Socket workerSender = new Socket(targetIp, 58111);
            OutputStream outputStreamWorker = workerSender.getOutputStream();
            BufferedWriter bwWorker = new BufferedWriter(new OutputStreamWriter(outputStreamWorker));
            System.out.println("Sending the request to the worker: " + targetIp);
            bwWorker.write("key:" + key + ",ip:" + selfIp + ",port:" + 58112);
            bwWorker.flush();
            outputStreamWorker.close();
            workerSender.close();

            // get result from the worker
            ServerSocket serverSocket = new ServerSocket(58112);
            Socket workerReceiver = serverSocket.accept();
            InputStream inputStreamWorker = workerReceiver.getInputStream();
            BufferedReader brWorker = new BufferedReader(new InputStreamReader(inputStreamWorker));
            String rst = brWorker.readLine();
            inputStreamWorker.close();
            serverSocket.close();
            System.out.println("Get result from the worker" + targetIp);
            System.out.println("Get result for: " + key + " is: " + rst);
            return rst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
