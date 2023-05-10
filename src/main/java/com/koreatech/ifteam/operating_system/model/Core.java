package com.koreatech.ifteam.operating_system.model;

import com.koreatech.ifteam.operating_system.model.packet.CorePacket;

enum State{
    Awake,
    Sleep
}

public class Core { // Core
    // Variables

    private int id; // 프로세서 ID, ex) 1코어, 2코어 ...
    private CoreMode mode; // 프로세서 모드, E/P/OFF
    private State state = State.Sleep; // 프로세서가 깨어 있는지 판별하는 변수
    private float powerUsage; // 전력 사용량?
    private int lastOperateTime;

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
    
    public CorePacket getPacket() {
        if (process == null) {
            return new CorePacket(-1, powerUsage);
        }
        return new CorePacket(process.id, powerUsage);
    }

    // Setter

    public void allocate(Process process) { // 프로세스 할당
        this.process = process;
        if (process == null) 
            System.out.println(id + "코어 Empty");
        else
        System.out.println(id+"코어를"+" p"+process.id+" 에 할당");
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
