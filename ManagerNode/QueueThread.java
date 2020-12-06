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
                        ClientInfo clientInfo = Manager.clientQueue.poll();
                        double queueTime = (double)(System.currentTimeMillis() - clientInfo.enqueueTime) / 1000.0;
                        System.out.println("client: " + clientInfo.client.toString() + "finish queueing and assigned to worker " + i);
                        System.out.println("Queue time is " + queueTime + "seconds");
                        new ManagerThread(clientInfo, Config.workerMap.get(i), i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

