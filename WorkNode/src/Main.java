import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args){

        ServerSocket serverSocket = new ServerSocket(58111);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String message = br.readLine();
        System.out.println("Receive message:" + message);
        inputStream.close();

//        String str = "bbbbb";
//        String md5 = Utils.encryptMD5(str);
//        String[] arr = {"aaaaa", "aaaaa"};
//        List<String[]> list = new ArrayList<>();
//        list.add(arr);
////        list.add(arr2);
//        String res = Cracker.findPassword(list,md5);
//        System.out.println("res:" + res);
    }
}
