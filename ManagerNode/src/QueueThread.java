
public class QueueThread implements Runnable{

    public QueueThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (Server.clientQueue.isEmpty()) continue;
                boolean flag = false;
                for (int i = 0; i < Server.avail.length; i++) {
                    if (Server.avail[i].compareAndSet(true, false)) {
                        flag = true;
                        System.out.println("Worker " + i + " is available.");
                        new ServerThread(Server.clientQueue.poll(), Config.workerMap.get(i), i);
                    }
                }
                if(!flag) {
                    System.out.println("No worker available now, wait......");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

