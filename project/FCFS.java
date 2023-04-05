package project;

public class FCFS implements ScheduleMethod{
    private static FCFS instance = new FCFS();
    private FCFS(){
        System.out.println("FCFS()");
    }
    public static FCFS getInstance(){
        return instance;
    }
    
    @Override
    public void clock() {
        run();
    }

    private void run(){

    }

};