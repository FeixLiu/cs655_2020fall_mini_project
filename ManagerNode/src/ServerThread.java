import java.io.*;
import java.net.Socket;

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

            System.out.println("The key is: " + key + " and the id is: " + id);

            // response an "OK" message to the client
//            OutputStream outputStream = socket.getOutputStream();
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
//            bw.write(message);
//            bw.flush();

            inputStream.close();
//            outputStream.close();
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