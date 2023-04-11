package project;

public class projectMain{
    public static void main(String[] args){
        /*ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(5, 5);
        ProcessManager.getInstance().addProcess(6, 3); // 교재 정보*/

        ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(6, 3);
        ProcessManager.getInstance().addProcess(5, 5); // 위 정보 순서만 바꿈

        ProcessManager.getInstance().printInfo();

        CoreMode modes[] = {CoreMode.E, CoreMode.OFF, CoreMode.OFF, CoreMode.OFF};

        CoreManager.getInstance().initCore(modes);

        ScheduleManager.getInstance().setMethod(RR.getInstance());

        do{
            SyncManager.getInstance().Update();
        } while(ProcessManager.getInstance().getProcessQueueSize() > 0 || !CoreManager.getInstance().isDoneCore());
    }
}