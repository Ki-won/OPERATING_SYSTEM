package project;

public class SyncManager{ // 동기식 동작을 위한 기준(주체), Clock 관리
    private static SyncManager instance = new SyncManager();
    protected SyncManager(){
        System.out.println("SyncManager()");
    }
    public static SyncManager getInstance(){
        return instance;
    }

    private int time = 0; // 전체적인 시간

    public int getTime(){ // 시간 getter
        return time;
    }
    
    public void Update(int clock_count){ // 객체들에게 clock 주기 송신
        for(int i=0; i < clock_count; ++i){
            System.out.println("\n--- time: "+time +" ---");  
            ProcessManager.getInstance().clockUpdate(); 
            ScheduleManager.getInstance().clockUpdate(); 
            CoreManager.getInstance().clockUpdate(); 
            ++time;
        }
    }
    
}
