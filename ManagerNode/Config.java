import java.io.*;
import java.util.*;

public class Config {

    public static int NUM_OF_WORKER;
    public static int WORKER_PORT_NUM;
    public static Map<Integer, WorkerInfo> workerMap = new HashMap<>();

    public static void init(String configFile){
        try {
            FileInputStream in = new FileInputStream(configFile);

            Scanner sc = new Scanner(in, "GBK");
            NUM_OF_WORKER = sc.nextInt();
            WORKER_PORT_NUM = sc.nextInt();
            sc.nextLine();
            int id = 0;
            for(int i = 0; i < NUM_OF_WORKER; i++){
                String line = sc.nextLine();
                String[] arr = line.split(" ");
                String managerIP = arr[0];
                String workerIP = arr[1];
                WorkerInfo worker = new WorkerInfo(id, managerIP, workerIP, WORKER_PORT_NUM);
                workerMap.put(id, worker);
                id++;
            }
            sc.close();
            System.out.println(workerMap.toString());
        } catch (IOException ignored) {
        }
    }
}
