package com.koreatech.ifteam.operating_system.model;

import com.koreatech.ifteam.operating_system.model.packet.CoreDataToUI;

enum State{
    Awake,
    Sleep
}

public class Core { // Core
    // Variables

    private int id; // 프로세서 ID, ex) 1코어, 2코어 ....
    private CoreMode mode; // 프로세서 모드, E/P/OFF
    private State state = State.Sleep; // 프로세서가 깨어 있는지 판별하는 변수
    private float powerUsage; // 전력 사용량?
    private int lastOperateTime; // 마지막 작동 시간

    // 한 프로세스 처리에 관련된 시간 관련 변수들
    private int startTime; // 프로세스 처리 시작 시간
    private int runTime; // 프로세스 처리에 걸린 시간
    private int endTime; // 프로세스 처리 종료 시간

    private Process process; // 할당된 프로세스

    Core(int id, CoreMode mode) {
        this.id = id;
        this.mode = mode;
    }
    
    // Getter

    public int getId(){ // ID getter
        return id;
    }

    public float getPowerUsage(){ // 전력 사용량 getter
        return powerUsage;
    }

    public int getLastOperateTime(){ // 마지막으로 코어를 사용한 시간
        return lastOperateTime;
    }

    public Process getProcess() { // 할당된 프로세스 getter
        return process;
    }

    public CoreMode getMode() {return mode;}
    

    // Setter.

    public void allocate(Process process) { // 프로세스 할당
        if (this.process != null && process == null) {
            endTime = SyncManager.getInstance().getClock();
            runTime = endTime - startTime + 1;
            pushToCorePacket();
            this.process = process;
            System.out.println(id + "코어 Empty");
        } else {
            if (this.process != null) {
                endTime = SyncManager.getInstance().getClock();
                runTime = endTime - startTime + 1;
                pushToCorePacket();
            }
            this.process = process;
            startTime = SyncManager.getInstance().getClock();
            System.out.println(id + "코어를" + " p" + process.id + " 에 할당");
        }
    }

    public void pushToCorePacket() {
        UIController.getInstance().ganttPacket.addCoreDataToUI(id, new CoreDataToUI(process.id, startTime, runTime, endTime));
    }
    
    // Functions

    public void awake(){ // 프로세서 가동 
        state = State.Awake;
        if(mode == CoreMode.E) powerUsage += 0.1f;
        else if (mode == CoreMode.P)
            powerUsage += 0.5f;
        System.out.println(id+"코어 Awake");
    }

    public void sleep(){ // 프로세서 휴면
        state = State.Sleep;
        System.out.println(id + "코어 Sleep");
    }

    public void operate(){ // 실행 = 프로세스 처리
        if(state == State.Sleep) awake(); // 휴면 상태면, 가동
        lastOperateTime = SyncManager.getInstance().getClock();
        
        if (mode == CoreMode.E) {
            System.out.println(id + "코어 동작: p" + process.id + " 처리(-1)");
            process.burst(1);
            powerUsage += 1.0f;
        } else if (mode == CoreMode.P) {
            System.out.println(id + "코어 동작: p" + process.id + " 처리(-2)");
            process.burst(2);
            powerUsage += 3.0f;
        }
    }

    public boolean isComplete() { // 프로세스 처리가 완료되었는가 = 프로세스가 종료되었는가
        if (process.getRemainTime() == 0) {
            System.out.println("                        [ " + process.getName() + " 프로세스 종료 ]");
            return true;
        }
        return false;
    }

    public void identifyStatus() {
        if (SyncManager.getInstance().getClock() - lastOperateTime >= 1) {
            sleep();
        }
    }
}
