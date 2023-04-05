package project;

class Process {
    private String id; // Process ID, ex) p1, p2, p3 ....
    private int arrivalTime; // 도착 시간
    private int currentTime; // 현재 시간(남은 시간)
    private int burstTime; // 총 실행 시간

    Process(int id, int arrivalTime, int burstTime){
        this.id = "p"+id;
        this.arrivalTime = arrivalTime;
        currentTime = this.burstTime = burstTime;
    }

    public String getId(){ // ID getter
        return id;
    }

    public int getArrivalTime(){ // 도착 시간 getter
        return arrivalTime;
    }

    public int getCurrentTime(){ // 현재(남은) 시간 getter
        return currentTime;
    }

    public int getBurstTime(){ // 총 실행 시간 getter 
        return burstTime;
    }

    public void burst(int amount){ // 실행, 현재 시간 감소시킴 
        if(currentTime - amount < 0){
            currentTime = 0;
        }else currentTime -= amount;
    }

    
}
