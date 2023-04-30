package com.koreatech.ifteam.operating_system.model.packet;

public class ProcessPacket {
    int id;
    int arrivalTime;
    int operateTime;
    int waitTime;
    int turnaroundTime;
    double normalizedTT;

    public ProcessPacket(int id, int arrivalTime, int operateTime, int waitTime, int turnaroundTime, double normalizedTT) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.operateTime = operateTime;
        this.waitTime = waitTime;
        this.turnaroundTime = turnaroundTime;
        this.normalizedTT = normalizedTT;
    }
}
