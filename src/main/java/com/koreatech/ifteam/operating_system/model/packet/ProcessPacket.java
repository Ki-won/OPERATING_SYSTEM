package com.koreatech.ifteam.operating_system.model.packet;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ProcessPacket {
    int name;
    int arrivalTime;
    int operateTime;
    int waitTime;
    int turnaroundTime;
    double normalizedTT;

    public int getName(){
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