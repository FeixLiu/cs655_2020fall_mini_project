import java.util.List;

public class Cracker {

    public static String findPassword(List<String[]> list, String target){
        int count = 0;
        for (String[] pair : list) {
            if (pair.length != 2) return null;
            String begin = pair[0];
            String end = pair[1];
            String str = begin;
            String md5Str = Utils.encryptMD5(str);
            boolean flag = true;
            while (!md5Str.equals(target)) {
                if (count % 1000000 == 0)
                    System.out.println("Handling request: " + target + ". Has processed to: " + str);
                count++;
                if (str.equals(end)) {
                    flag = false;
                    break;
                }
                str = Utils.findNextStr(str);
                md5Str = Utils.encryptMD5(str);
            }
            if (flag && str != null && !str.equals("")) return str;
        }
        return null;
    }

}
