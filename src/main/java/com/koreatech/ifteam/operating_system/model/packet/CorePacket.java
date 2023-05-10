package com.koreatech.ifteam.operating_system.model.packet;

public class CorePacket {
    public int[] processIdList = new int[4];
    public float[] powerUsageList = new float[4];

    public CorePacket(int[] processId, float[] powerUsage) {
        this.processIdList = processId;
        this.powerUsageList = powerUsage;
    }
}
