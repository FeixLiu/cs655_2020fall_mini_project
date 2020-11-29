import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args){
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(58111);
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String message = br.readLine();
                System.out.println("Receive message: " + message);
                String[] messages = message.split(",");
                String key = messages[0].split(":")[1];
                String ip = messages[1].split(":")[1];
                int port = Integer.parseInt(messages[2].split(":")[1]);

                inputStream.close();
                String[] arr = {"aaaaa", "ZZZZZ"};
                List<String[]> list = new ArrayList<>();
                list.add(arr);
                String res = Cracker.findPassword(list, key);
                System.out.println(res);

                Socket rstSender = new Socket(ip, port);
                OutputStream outputStream = rstSender.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
                bw.write(res);
                bw.flush();
                outputStream.close();
                socket.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
