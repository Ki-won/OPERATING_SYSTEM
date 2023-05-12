package com.koreatech.ifteam.operating_system.model.packet;

public class CoreDataToUI {
    public int processId;
    public int startTime;
    public int runTime;
    public int endTime;

    public CoreDataToUI(int processId, int startTime, int runTime, int endTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.runTime = runTime;
        this.endTime = endTime;
    }
}
