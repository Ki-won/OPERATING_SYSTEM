package com.koreatech.ifteam.operating_system.model.scheduling;

import com.koreatech.ifteam.operating_system.model.*;

public class CUSTOM implements ScheduleMethod {
    private static CUSTOM instance = new CUSTOM();
    private CUSTOM(){
        System.out.println("CUSTOM()");
        init();
    }
    public static CUSTOM getInstance(){
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
    }

    private void run(){
        // 1. readyQ에서 프로세스 받아오기 && 할당
        com.koreatech.ifteam.operating_system.model.Process putProcess = null;

        while(!ProcessManager.getInstance().empty_readyQueue() && CoreManager.getInstance().selectablePCore()){
            com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().poll_readyQueue(); // readyQ가 비어있을수도 있지. 이때 getProcess = null
            if(getProcess == null) break; // useless

            int position = CoreManager.getInstance().getPCorePosition(); // 할당받을 코어의 위치를 받아옴
            CoreManager.getInstance().allocateAtForCustom(getProcess, position);
        } // P코어 할당 방식

        while((!ProcessManager.getInstance().empty_readyQueue() || !ProcessManager.getInstance().emptyOneBrustQueue()) && CoreManager.getInstance().selectableECore()){
            if(!ProcessManager.getInstance().emptyOneBrustQueue()){ // 버스트 타임이 1초인 프로세스가 있다면
                com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().pollOneBrustQ();
                int position = CoreManager.getInstance().getECorePosition(); // 할당받을 코어의 위치를 받아옴

                CoreManager.getInstance().allocateAtForCustom(getProcess, position);
            }
            else{ // 버스트 타임이 1초인 프로세스가 없다면
                com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().poll_readyQueue();
                int position = CoreManager.getInstance().getECorePosition(); // 할당받을 코어의 위치를 받아옴
                CoreManager.getInstance().allocateAtForCustom(getProcess, position);
            }
        } // E코어 할당 방식

        for(int i = 0; i < coreNum; ++i){
            Core getCore = CoreManager.getInstance().getCore(i);
            if(getCore.getProcess() != null){
                if(CoreManager.getInstance().operating(i)){ // 프로세스 처리 완료
                    ProcessManager.getInstance().saveToResultList(getCore.getProcess()); // 프로세스가 종료되면 그 정보를 넣어줌
                    ProcessManager.getInstance().clockUpdate();

                    if(getCore.getMode() == CoreMode.P){
                        if(!ProcessManager.getInstance().empty_readyQueue()){ //레디 큐에 프로세스가 기다리고 있으면
                            com.koreatech.ifteam.operating_system.model.Process getProcess = ProcessManager.getInstance().getMinRRProcess(SyncManager.getInstance().getClock()); // 그 프로세스 가져와서
                            CoreManager.getInstance().overwriteAt(i, getProcess); // 코어를 종료하지 않고 바로 넣음
                        }
                        else
                            CoreManager.getInstance().removeProcess(i);
                    }
                    else{
                        if(!ProcessManager.getInstance().emptyOneBrustQueue()) { // 원버스트 큐에 값이 있다면
                            com.koreatech.ifteam.operating_system.model.Process getProcessFromOneBrustQ = ProcessManager.getInstance().pollOneBrustQ();
                            // 원버스트 큐에 있는 것 들고와서
                            CoreManager.getInstance().overwriteAt(i, getProcessFromOneBrustQ); // 프로세스 교체
                        }
                        else if(!ProcessManager.getInstance().empty_readyQueue()){ // 원버스트 큐에는 없지만 레디큐에는 있다면
                            com.koreatech.ifteam.operating_system.model.Process getProcessFromReadyQ = ProcessManager.getInstance().poll_readyQueue();
                            //레디큐거 들고와서
                            CoreManager.getInstance().overwriteAt(i, getProcessFromReadyQ); // 프로세스 교체
                        }
                        else{
                            CoreManager.getInstance().removeProcess(i); //둘다 없다면 프로세스 삭제
                        }
                    }

                    roundTime[i] = 0;
                }else{
                    ProcessManager.getInstance().clockUpdate();
                    if(getCore.getMode() == CoreMode.P){ // 해당 코어가 p코어일 때
                        if (getCore.getProcess().getRemainTime() == 1){ // 가지고 있는  프로세스의 남은 시간이 1일 때
                            ProcessManager.getInstance().pushOneBrustQ(getCore.getProcess()); // 1초 큐에 집어넣는다.

                            if(!ProcessManager.getInstance().empty_readyQueue()) { // 레디큐가 비어있지 않으면
                                com.koreatech.ifteam.operating_system.model.Process getProcessFromReadyQ = ProcessManager.getInstance().poll_readyQueue();
                                CoreManager.getInstance().overwriteAt(i, getProcessFromReadyQ); // 프로세스 프로세스 교체
                            }
                            else{ //레디큐가 비워져 있다면
                                CoreManager.getInstance().removeProcess(i); //프로세스 삭제
                            }

                            roundTime[i] = 0;
                        }
                        else
                            ++roundTime[i];
                    }
                    else{ // 해당 코어가 E코어 일때
                        if(getCore.getProcess().getRemainTime() > 1){
                            if(!ProcessManager.getInstance().emptyOneBrustQueue()) { // 원버스트큐가 비어있지 않으면
                                ProcessManager.getInstance().push_readyQueue(getCore.getProcess());
                                com.koreatech.ifteam.operating_system.model.Process getProcessFromOneBrustQ = ProcessManager.getInstance().pollOneBrustQ();
                                CoreManager.getInstance().overwriteAt(i, getProcessFromOneBrustQ); // 프로세스 프로세스 교체
                                roundTime[i] = 0;
                            }
                        }
                        else
                            ++roundTime[i];
                    }
                }
            }
        }
    }
}
