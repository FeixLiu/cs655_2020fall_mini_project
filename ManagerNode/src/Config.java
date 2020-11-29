import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Config {

    public static int NUM_OF_WORKER;
    public static Map<Integer, WorkerInfo> workerMap = new HashMap<>();

    public static void init(String configFile){
        try {
            FileInputStream in = new FileInputStream(configFile);

            Scanner sc = new Scanner(in, "GBK");
            NUM_OF_WORKER = sc.nextInt();
            sc.nextLine();
            int id = 0;
            for(int i = 0; i < NUM_OF_WORKER; i++){
                String line = sc.nextLine();
                String[] arr = line.split(" ");
                String managerIP = arr[0];
                String workerIP = arr[1];
                int port = Integer.valueOf(arr[2]);
                WorkerInfo worker = new WorkerInfo(id, managerIP, workerIP, port);
                workerMap.put(id, worker);
                id++;
            }
            sc.close();
            System.out.println(workerMap.toString());
        } catch (IOException e) {
        }
    }
}