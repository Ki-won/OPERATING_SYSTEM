package com.koreatech.ifteam.operating_system.model.scheduling;

import com.koreatech.ifteam.operating_system.model.*;

public class FCFS implements ScheduleMethod {
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
        CoreManager.getInstance().printInfo();
        run();
    }

    private void run(){
        // 1. readyQ에서 프로세스 받아오기 && 할당

        while(!ProcessManager.getInstance().empty_readyQueue() && CoreManager.getInstance().selectable()){
            com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().poll_readyQueue(); // readyQ가 비어있을수도 있지. 이때 getProcess = null
            if(getProcess == null) break; // useless 이거 필요한가?

            CoreManager.getInstance().allocateAt(getProcess); // 코어에 프로세스 할당 시도
        }

        for(int i = 0; i < coreNum; ++i){
            Core getCore = CoreManager.getInstance().getCore(i);
            if(getCore.getProcess() != null){
                if(CoreManager.getInstance().operating(i)){ // 프로세스 처리 완료
                    ProcessManager.getInstance().saveToResultList(getCore.getProcess()); // 프로세스가 종료되면 그 정보를 넣어줌
                    ProcessManager.getInstance().clockUpdate();

                    if(!ProcessManager.getInstance().empty_readyQueue()){ //레디 큐에 프로세스가 기다리고 있으면
                        com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().poll_readyQueue(); // 그 프로세스 가져와서
                        CoreManager.getInstance().maintainCore(i, getProcess); // 코어를 종료하지 않고 바로 넣음
                    }
                    else
                        CoreManager.getInstance().removeProcess(i);

                    roundTime[i] = 0;
                }else{
                    ++roundTime[i];
                }
            }
        }
    }

};