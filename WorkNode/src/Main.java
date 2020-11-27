import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){

        String str = "bbbbb";
        String md5 = Utils.encryptMD5(str);
        String[] arr = {"aaaaa", "aaaaa"};
        List<String[]> list = new ArrayList<>();
        list.add(arr);
//        list.add(arr2);
        String res = Cracker.findPassword(list,md5);
        System.out.println("res:" + res);
    }
}
