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

    private void init(){
        coreNum = CoreManager.getInstance().getCoreNum();
    }

    @Override
    public void clock() {
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
            if (getCore != null && getCore.getProcess() != null){
                if (CoreManager.getInstance().operating(i)) { // 프로세스 처리 완료
                    ProcessManager.getInstance().saveToResultList(getCore.getProcess()); // 프로세스가 종료되면 그 정보를 넣어줌
                    
                    CoreManager.getInstance().removeProcess(i);
                }
            }
        }
    }

};