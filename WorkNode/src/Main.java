import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(58111);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String message = br.readLine();
            System.out.println("Receive message: " + message);
            inputStream.close();
            String[] arr = {"aaaaa", "ZZZZZ"};
            List<String[]> list = new ArrayList<>();
            list.add(arr);
            String res = Cracker.findPassword(list, message);
            System.out.println(res);
            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(res);
            bw.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
