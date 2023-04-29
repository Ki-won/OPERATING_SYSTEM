package project;

public class projectMain{
    public static void main(String[] args){
        ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(5, 5);
        ProcessManager.getInstance().addProcess(6, 3); // 교재 정보

        /*ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(6, 3);
        ProcessManager.getInstance().addProcess(5, 5); // 위 정보 순서만 바꿈*/

        ProcessManager.getInstance().printInfo();

        CoreMode modes[] = {CoreMode.P, CoreMode.P, CoreMode.E, CoreMode.E};

        CoreManager.getInstance().initCore(modes);

        ScheduleManager.getInstance().setMethod(HRRN.getInstance());

        do{
            SyncManager.getInstance().Update();
        } while(ProcessManager.getInstance().getProcessQueueSize() > 0 || !CoreManager.getInstance().isDoneCore() || ProcessManager.getInstance().getReadyQueueSize() > 0);

        ProcessManager.getInstance().printResult();
    }
}