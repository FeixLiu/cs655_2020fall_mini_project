public class QueueThread implements Runnable{
    public QueueThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (Server.clientQueue.isEmpty()) continue;
                for (int i = 0; i < Server.avail.length; i++) {
                    if (Server.avail[i].compareAndSet(true, false)) {
//                        System.out.println("Worker " + i + " is available.");
                        new ServerThread(Server.clientQueue.poll(), Config.workerMap.get(i), i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

