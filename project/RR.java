package project;

public class RR implements ScheduleMethod{
    private static RR instance = new RR();
    private RR(){
        System.out.println("RR()");
        init();
    }
    public static RR getInstance(){
        return instance;
    }
    
    private int coreNum;
    private int limitTime;
    private int roundTime[];

    private void init(){
        coreNum = CoreManager.getInstance().getCoreNum();
        limitTime = ScheduleManager.getInstance().getValue();
        roundTime = new int[coreNum];
        for(int i=0;i<coreNum;++i){
            roundTime[i] = 0;
        }
    }
    
    @Override
    public void clock() {
        run();
        CoreManager.getInstance().printInfo();
    }

    private void run(){
        // 1. readyQ에서 프로세스 받아오기 && 할당
        
        while(!ProcessManager.getInstance().empty_readyQueue() && CoreManager.getInstance().selectable()){
            Process getProcess = ProcessManager.getInstance().poll_readyQueue(); // readyQ가 비어있을수도 있지. 이때 getProcess = null 
            if(getProcess == null) break; // useless
            
            CoreManager.getInstance().allocateAt(getProcess); // 코어에 프로세스 할당 시도
        }

        for(int i = 0; i < coreNum; ++i){
            Core getCore = CoreManager.getInstance().getCore(i);
            if(getCore.getProcess() != null){
                if(CoreManager.getInstance().operating(i)){ // 프로세스 처리 완료
                    CoreManager.getInstance().removeProcess(i); 
                    roundTime[i] = 0;
                }else{
                    ++roundTime[i];
                    if(roundTime[i] == limitTime){ // 제한 시간 만료되었을 때
                        Process getProcess = CoreManager.getInstance().getCore(i).getProcess(); // 프로세스 가져옴
                        CoreManager.getInstance().removeProcess(i); // 프로세스 삭제
                        ProcessManager.getInstance().push_readyQueue(getProcess); // 프로세스 readyQ에 다시 삽입
                        roundTime[i] = 0;
                    }
                }
            }
        }
    }

  
}
