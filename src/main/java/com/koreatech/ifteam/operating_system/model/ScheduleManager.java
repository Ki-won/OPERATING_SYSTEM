package com.koreatech.ifteam.operating_system.model;


import com.koreatech.ifteam.operating_system.model.scheduling.*;

public class ScheduleManager{ // 스케줄링 방식 관리
    private static ScheduleManager instance = new ScheduleManager();
    protected ScheduleManager(){
        System.out.println("ScheduleManager()");
    }

    public static ScheduleManager getInstance() {
        return instance;
    }

    // Variables
    
    private int value = 2; // 추가 변수, 일단은 RoundRobin을 실행할 때만 사용
    private ScheduleMethod method = null; // 스케줄링 방식

    public void clockUpdate() { // Clock 주기
        method.clock(); // 지정된 스케줄링 방식으로 실행
    }

    // getter
    
    public int getValue() { // 추가 변수 getter
        return value;
    }

    // setter
        
    public void setMethod(ScheduleMethod method) { // 스케줄링 방식 지정
        this.method = method;
    }
    
    public void setMethod(int methodIndex) { // 스케줄링 방식 지정 오버라이딩
        switch (methodIndex) {
            case 0:
                this.method = FCFS.getInstance();
            case 1:
                this.method = RR.getInstance();
            case 2:
                this.method = SPN.getInstance();
            case 3:
                this.method = SRTN.getInstance();
            case 4:
                this.method = HRRN.getInstance();
            default:
                System.out.println("Invalid Scheduling Method: " + methodIndex);
                return;
        }
    }

   


}
