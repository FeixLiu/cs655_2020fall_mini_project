import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Utils {
    /**
     * find the next string. Eg: the next of aaaaa is aaaab
     * @param str current string
     * @return the next string
     */
    public static String findNextStr(String str){
        StringBuilder result = new StringBuilder();
        if(str.matches("[Z]+")) return result.toString();
        int[] arr = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            arr[i] = getNumByCharacter(str.charAt(i));
        }
        int index = arr.length-1;
        arr[index] += 1;
        while(index > 0) {
            if(arr[index] == 52) {
                arr[index] %= 52;
                arr[index-1] += 1;
                index--;
            }
            else {
                break;
            }
        }
        for (int value : arr) {
            result.append(getCharacterByNum(value));
        }
        return result.toString();
    }

    /**
     * convert character to related number
     * @param c character a-zA-Z
     * @return related number 0-51
     */
    public static int getNumByCharacter(char c){
        if(c >= 'a' && c <= 'z') {
            return c - 'a';
        }
        else if(c >= 'A' && c <= 'Z'){
            return c - 'A' + 26;
        }
        return -1;
    }

    /**
     * convert number to related character
     * @param n 0-51
     * @return a-zA-Z
     */
    public static char getCharacterByNum(int n){
        if(n >= 0 && n <= 25) {
            return (char)('a' + n);
        }
        else{
            return (char)('A'+n-26);
        }
    }

    /**
     * encrypt string with MD5
     * @param dataStr the original string
     * @return the string encrypted with MD5
     */
    public static String encryptMD5(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
