package project;

public class projectMain{
    public static void main(String[] args){
        ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(5, 5);
        ProcessManager.getInstance().addProcess(6, 3);

        ProcessManager.getInstance().printInfo();

        CoreMode modes[] = {CoreMode.E, CoreMode.E, CoreMode.OFF, CoreMode.OFF};

        CoreManager.getInstance().initCore(modes);

        ScheduleManager.getInstance().setMethod(RR.getInstance());   
        SyncManager.getInstance().Update(20); // run
    }
}