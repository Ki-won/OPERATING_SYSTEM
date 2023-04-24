package project;

class Process implements Comparable<Process> {
    private String id; // Process ID, ex) p1, p2, p3 ....
    private int arrivalTime; // 도착 시간
    private int remainTime; // 남은 시간
    private int burstTime; // 총 실행에 필요한 시간
    private int turnaroundTime; // 반환 시간
    private int waitTime; // 대기 시간
    private int operateTime; // 총 operate 횟수 (코어에서 실행한 clock)
    
    Process(int id, int arrivalTime, int burstTime){
        this.id = "p"+id;
        this.arrivalTime = arrivalTime;
        remainTime = this.burstTime = burstTime;
        turnaroundTime = 0;
        waitTime = 0;
        operateTime = 0;
    }

    public String getId(){ // ID getter
        return id;
    }

    public int getArrivalTime(){ // 도착 시간 getter
        return arrivalTime;
    }

    public int getRemainTime(){ // 현재(남은) 시간 getter
        return remainTime;
    }

    public int getBurstTime(){ // 총 실행 시간 getter
        return burstTime;
    }

    public int getTurnaroundTime(){return turnaroundTime;}

    public void setTurnaroundTime(int time){this.turnaroundTime = time;}
    public void setWaitTime(int time){this.waitTime = time;}
    public void setNormalizedTT(double time){this.normalizedTT = time;}

    public void printInfo(){
        System.out.print(id + ": ");
        System.out.printf("AT: %d BT: %d WT: %d TT: %d NTT: %f", arrivalTime, operateTime, waitTime, turnaroundTime, (float)(turnaroundTime/burstTime ));
        System.out.println();
    }

    public void burst(int amount) { // 실행, 현재 시간 감소시킴
        ++operateTime; // 
        if(remainTime - amount < 0){
            remainTime = 0;
        }else remainTime -= amount;
    }

    // 도착시간을 비교하여 정렬함
    @Override
    public int compareTo(Process o) {
        if(this.arrivalTime > o.getArrivalTime())
            return 1;
        else if(this.arrivalTime < o.getArrivalTime())
            return -1;
        else
            return 0;
    }
}
