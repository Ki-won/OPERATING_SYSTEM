package com.koreatech.ifteam.operating_system.model;

public class SyncManager{ // 동기식 동작을 위한 기준(주체), Clock 관리
    private static SyncManager instance = new SyncManager();
    protected SyncManager(){
        System.out.println("SyncManager()");
    }

    public static SyncManager getInstance() {
        return instance;
    }
    
    // Variable

    private int clock = 0; // 전체적인 시간
    private boolean interrupt = false;

    // Getter

    public int getClock() { // 시간 getter
        return clock;
    }
    
    // Setter
    public void Interrupt() {
        interrupt = true;
    }

    // Functions
    
    public void Update() { // 객체들에게 clock 주기 송신
        System.out.println("\n--- time: " + clock + " ---");
        ScheduleManager.getInstance().clockUpdate();
        CoreManager.getInstance().clockUpdate();
        ProcessManager.getInstance().clockUpdate();
        UIController.getInstance().coreSend();
        ++clock;
    }

    public void run() {
        do {
            SyncManager.getInstance().Update();
        } while ((ProcessManager.getInstance().getProcessQueueSize() > 0 || !CoreManager.getInstance().isDoneCore()
                || ProcessManager.getInstance().getReadyQueueSize() > 0) && !interrupt);
        if (interrupt) {
            interrupt = false;
            UIController.getInstance().StateSend(1);
            System.out.println("Interrupt: clock_" + clock);
        } else
            UIController.getInstance().StateSend(0);
    }
    
}
