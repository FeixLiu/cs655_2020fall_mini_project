import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.net.http.HttpResponse;

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
            System.out.println("Receive message:" + message);

            message = message.split(" ")[1];
            String key = message.substring(6, 11);
            int id = Integer.parseInt(message.substring(15));

            String res = "The key is: " + key + " and the id is: " + id;

            String response = "";
            response += "HTTP/1.1 200 OK\n";
            response += "Content-Type: text\\plain\n";
            response += "Content-Length: " + res.length() + '\n';
            response += "\n";
            response += res;

            // response an "OK" message to the client
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(response);
            bw.flush();

            inputStream.close();
            outputStream.close();
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
}