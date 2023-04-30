package com.koreatech.ifteam.operating_system.model.scheduling;

import com.koreatech.ifteam.operating_system.model.*;

public class RR implements ScheduleMethod {
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
    }

    private void run(){
        // 1. readyQ에서 프로세스 받아오기 && 할당

        while(!ProcessManager.getInstance().empty_readyQueue() && CoreManager.getInstance().selectable()){
            com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().poll_readyQueue(); // readyQ가 비어있을수도 있지. 이때 getProcess = null
            if(getProcess == null) break; // useless

            CoreManager.getInstance().allocateAt(getProcess); // 코어에 프로세스 할당 시도
        }

        for(int i = 0; i < coreNum; ++i){
            Core getCore = CoreManager.getInstance().getCore(i);
            if(getCore.getProcess() != null){
                if(CoreManager.getInstance().operating(i)){ // 프로세스 처리 완료
                    ProcessManager.getInstance().saveToResultList(getCore.getProcess()); // 프로세스가 종료되면 그 정보를 넣어줌
                    ProcessManager.getInstance().clockUpdate();
                    if(!ProcessManager.getInstance().empty_readyQueue()){
                        com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().poll_readyQueue();
                        CoreManager.getInstance().maintainCore(i, getProcess);
                    }
                    else
                        CoreManager.getInstance().removeProcess(i);
                    roundTime[i] = 0;
                }else{
                    ++roundTime[i];
                    if(roundTime[i] == limitTime){ // 제한 시간 만료되었을 때
                        com.koreatech.ifteam.operating_system.model.Process getProcess = CoreManager.getInstance().getCore(i).getProcess(); // 코어에 있는 프로세스 가져옴
                        ProcessManager.getInstance().clockUpdate();
                        if(!ProcessManager.getInstance().empty_readyQueue()) { // 레디큐가 비어있지 않으면
                            com.koreatech.ifteam.operating_system.model.Process getProcessFromReadyQ = ProcessManager.getInstance().poll_readyQueue();
                            CoreManager.getInstance().maintainCore(i, getProcessFromReadyQ); // 프로세스 프로세스 교체
                        }
                        else{ //레디큐가 비워져 있다면
                            CoreManager.getInstance().removeProcess(i); //프로세스 삭제
                        }
                        ProcessManager.getInstance().push_readyQueue(getProcess); // 프로세스 readyQ에 다시 삽입
                        roundTime[i] = 0;
                    }
                }
            }
        }
    }

  
}
