package com.koreatech.ifteam.operating_system.model.packet;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ProcessPacket {
    IntegerProperty name;
    IntegerProperty arrivalTime;
    IntegerProperty operateTime;
    IntegerProperty waitTime;
    IntegerProperty turnaroundTime;
    DoubleProperty normalizedTT;

    public int getName(){
        return name.get();
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public int getOperateTime() {
        return operateTime.get();
    }

    public int getWaitTime() {
        return waitTime.get();
    }

    public int getTurnaroundTime() {
        return turnaroundTime.get();
    }

    public double getNormalizedTT() {
        return normalizedTT.get();
    }

    public ProcessPacket(int name, int arrivalTime, int operateTime, int waitTime, int turnaroundTime, double normalizedTT) {
        this.name = new SimpleIntegerProperty(name);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.operateTime = new SimpleIntegerProperty(operateTime);
        this.waitTime = new SimpleIntegerProperty(waitTime);
        this.turnaroundTime = new SimpleIntegerProperty(turnaroundTime);
        this.normalizedTT = new SimpleDoubleProperty(normalizedTT);
    }
}