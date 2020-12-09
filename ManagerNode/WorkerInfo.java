public class WorkerInfo {
    // save one worker's info such as the worker's ip and port number
    // and the corresponding manager's IP

    public String workerIp;
    public String managerIp;
    public int port;
    public int id;

    public WorkerInfo(int id, String managerIp, String workerIp, int port) {
        this.workerIp = workerIp;
        this.managerIp = managerIp;
        this.port = port;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", manager IP: " + managerIp +  ", worker IP: " + workerIp + ", Port: " + port;
    }
}
