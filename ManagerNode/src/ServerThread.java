import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerThread implements Runnable{
    private Socket socket;
    private WorkerInfo worker;
    private int id;
    public ServerThread(Socket client, WorkerInfo worker, int id) {
        this.socket = client;
        this.worker = worker;
        this.id = id;
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
            int userId = Integer.parseInt(message.substring(42));
            System.out.println("Received a request: " + key);

            String rst = getResult(key, worker.workerIp, worker.managerIp, worker.port);

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
            System.out.println("Set avail id " + this.id + " to true");
            Server.avail[this.id].set(true);
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

    public String getResult(String key, String targetIp, String selfIp, int port) {
        try {
            // assign the task to a worker
            ServerSocket serverSocket;
            int rstPort = 58000;
            while (true) {
                // find an appropriate port to receive the number
                try {
                    serverSocket = new ServerSocket(rstPort);
                    break;
                } catch (Exception e) {
                    rstPort++;
                }
            }
            Socket workerSender = new Socket(targetIp, port);
            OutputStream outputStreamWorker = workerSender.getOutputStream();
            BufferedWriter bwWorker = new BufferedWriter(new OutputStreamWriter(outputStreamWorker));
            System.out.println("Sending the request to the worker: " + targetIp);
            bwWorker.write("key:" + key + ",ip:" + selfIp + ",port:" + rstPort);
            bwWorker.flush();
            outputStreamWorker.close();
            workerSender.close();

            // get result from the worker
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
