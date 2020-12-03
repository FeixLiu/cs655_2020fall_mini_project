public class QueueThread implements Runnable{
    public QueueThread() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (Manager.clientQueue.isEmpty()) continue;
                for (int i = 0; i < Manager.avail.length; i++) {
                    if (Manager.avail[i].compareAndSet(true, false) && !Manager.clientQueue.isEmpty()) {
                        new ManagerThread(Manager.clientQueue.poll(), Config.workerMap.get(i), i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

