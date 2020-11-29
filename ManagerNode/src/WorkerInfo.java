public class WorkerInfo {
    public String workerIp;
    public String managerIp;
    public int port;
    public int id;

    public WorkerInfo(){

    }

    public WorkerInfo(int id, String managerIp, String workerIp, int port) {
        this.workerIp = workerIp;
        this.managerIp = managerIp;
        this.port = port;
        this.id = id;
    }

    @Override
    public String toString() {
        String str = "ID: " + id + ", manager IP: " + managerIp +  ", worker IP: " + workerIp + ", Port: " + port;
        return str;
    }
}
