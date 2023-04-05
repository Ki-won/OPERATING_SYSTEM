package project;

public class FCFS implements ScheduleMethod{
    private static FCFS instance = new FCFS();
    private FCFS(){
        System.out.println("FCFS()");
        init();
    }
    public static FCFS getInstance(){
        return instance;
    }

    private int coreNum;
    private int roundTime[];

    private void init(){
        coreNum = CoreManager.getInstance().getCoreNum();
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
                }
            }
        }
    }

};