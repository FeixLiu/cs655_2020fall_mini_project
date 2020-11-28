import java.util.List;

public class Cracker {

    public static String findPassword(List<String[]> list, String target){
        for(int i = 0; i < list.size(); i++) {
            String[] pair = list.get(i);
            if(pair.length != 2) return "------------";
            String begin = pair[0];
            String end = pair[1];
            String str = begin;
            String md5Str = Utils.encryptMD5(str);
            boolean flag = true;
            while(!md5Str.equals(target)) {
                System.out.println(str);
                if(str.equals(end)) {
                    flag = false;
                    break;
                }
                str = Utils.findNextStr(str);
                md5Str = Utils.encryptMD5(str);
            }
            if(flag && str != null && !str.equals("")) return str;
        }
        return "-------";
    }

}
