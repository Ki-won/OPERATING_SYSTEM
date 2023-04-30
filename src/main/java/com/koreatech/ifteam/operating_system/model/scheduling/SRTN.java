package com.koreatech.ifteam.operating_system.model.scheduling;

import com.koreatech.ifteam.operating_system.model.*;

public class SRTN implements ScheduleMethod {
    private static SRTN instance = new SRTN();
    private SRTN(){
        System.out.println("SRTN()");
        init();
    }
    public static SRTN getInstance(){
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
        CoreManager.getInstance().printInfo();
        run();
    }

    private void run(){
        // 1. readyQ에서 프로세스 받아오기 && 할당

        while(!ProcessManager.getInstance().empty_readyQueue() && CoreManager.getInstance().selectable()){
            com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().getMinRemainProcess(); // 남은 시간이 적은 프로세스 가져옴
            if(getProcess == null) break; // useless

            CoreManager.getInstance().allocateAt(getProcess); // 코어에 프로세스 할당 시도
        }

        for(int i = 0; i < coreNum; ++i){
            Core getCore = CoreManager.getInstance().getCore(i);
            if(getCore.getProcess() != null){
                ProcessManager.getInstance().clockUpdate();

                if(CoreManager.getInstance().operating(i)){ // 프로세스 처리 완료
                    ProcessManager.getInstance().saveToResultList(getCore.getProcess()); // 프로세스가 종료되면 그 정보를 넣어줌

                    if(!ProcessManager.getInstance().empty_readyQueue()){
                        com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().getMinRemainProcess();
                        CoreManager.getInstance().maintainCore(i, getProcess);
                    }
                    else
                        CoreManager.getInstance().removeProcess(i);
                    roundTime[i] = 0;
                }else{
                        com.koreatech.ifteam.operating_system.model.Process getProcess = CoreManager.getInstance().getCore(i).getProcess(); // 코어에 있는 프로세스 가져옴
                        if(!ProcessManager.getInstance().empty_readyQueue()) { // 레디큐가 비어있지 않으면
                            ProcessManager.getInstance().push_readyQueue(getProcess);
                            com.koreatech.ifteam.operating_system.model.Process getProcessFromReadyQ = ProcessManager.getInstance().getMinRemainProcess();
                            CoreManager.getInstance().maintainCore(i, getProcessFromReadyQ); // 프로세스 프로세스 교체
                        }
                }
            }
        }
    }
}
