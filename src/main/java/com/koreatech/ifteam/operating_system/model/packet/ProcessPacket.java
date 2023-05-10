package com.koreatech.ifteam.operating_system.model.packet;

public class ProcessPacket {
    int name;
    int arrivalTime;
    int operateTime;
    int waitTime;
    int turnaroundTime;
    double normalizedTT;

    int getName(){
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getOperateTime() {
        return operateTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public double getNormalizedTT() {
        return normalizedTT;
    }

    public ProcessPacket(int name, int arrivalTime, int operateTime, int waitTime, int turnaroundTime, double normalizedTT) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.operateTime = operateTime;
        this.waitTime = waitTime;
        this.turnaroundTime = turnaroundTime;
        this.normalizedTT = normalizedTT;
    }
}
