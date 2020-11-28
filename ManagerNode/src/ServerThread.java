import java.io.*;
import java.net.Socket;
import java.util.HashMap;

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
            String ip = socket.getRemoteSocketAddress().toString();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String message = br.readLine();
            System.out.println("Receive message:" + message + " from ip: " + ip);

            message = message.split(" ")[1];
            String key = message.substring(6, 11);
            int id = Integer.parseInt(message.substring(15));

            HashMap<String, String> response = new HashMap<>();
            response.put("message", "The key is: " + key + " and the id is: " + id);
            response.put("code", "200");


            System.out.println(response.toString());

            // response an "OK" message to the client
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(response.toString());
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