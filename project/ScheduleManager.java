package project;

public class ScheduleManager{ // 스케줄링 방식 관리
    private static ScheduleManager instance = new ScheduleManager();
    protected ScheduleManager(){
        System.out.println("ScheduleManager()");
    }
    public static ScheduleManager getInstance(){
        return instance;
    }

    private int value = 2; // 추가 변수, 일단은 RoundRobin을 실행할 때만 사용함

    private ScheduleMethod method = null; // 스케줄링 방식

    public void clockUpdate(){ // Clock 주기
        method.clock(); // 지정된 스케줄링 방식으로 실행
    }
        
    public void setMethod(ScheduleMethod method){ // 스케줄링 방식 지정
        this.method = method;
    }

    public int getValue(){ // 추가 변수 getter
        return value;
    }


}
