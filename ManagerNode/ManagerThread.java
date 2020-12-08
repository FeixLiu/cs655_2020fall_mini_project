import java.io.*;
import java.net.*;

public class ManagerThread implements Runnable{
    private Socket socket;
    private WorkerInfo worker;
    private int id;
    private String message;

    public ManagerThread(ClientInfo clientInfo, WorkerInfo worker, int id) {
        this.socket = clientInfo.client;
        this.message = clientInfo.message;
        this.worker = worker;
        this.id = id;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            // process the message sent from the client
            message = message.split(" ")[1];    // extract the md5 string from the message
            String key = message.substring(6, 38);
            System.out.println("The worker: " + id + " received a request: " + key);

            long startTime = System.currentTimeMillis();    // start time of the cracking task
            String rst = getResult(key, worker.workerIp, worker.managerIp, worker.port);
            long endTime = System.currentTimeMillis();  // end time of the cracking task
            System.out.println("The worker: " + id + " used " + ((double) (endTime - startTime)) / 1000.0 + " seconds for the request: " + key);

            // generate the HTTP respond with the result of the cracking
            String response = "";
            response += "HTTP/1.1 200 OK\n";
            response += "Access-Control-Allow-Origin:*\n";
            response += "Content-Type: text\\plain\n";
            response += "Content-Length: " + rst.length() + '\n';
            response += "\n";
            response += rst;

            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(response);
            bw.flush();

            outputStream.close();

            // make the worker available  to other request
            Manager.avail[this.id].set(true);
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
            System.out.println("The worker: " + id + " sent the request to: " + targetIp);
            // send the request to the worker
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
            serverSocket.close();   // close the manager to worker connection
            System.out.println("The worker: " + id + " got result from: " + targetIp);
            System.out.println("The worker: " + id + " got result for: " + key + " is: " + rst);
            return rst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
