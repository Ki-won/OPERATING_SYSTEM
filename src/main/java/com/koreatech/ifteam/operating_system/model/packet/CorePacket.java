package com.koreatech.ifteam.operating_system.model.packet;

public class CorePacket {
    public int[] preocessIdList;
    public float[] powerUsageList;

    public CorePacket(int[] processId, float[] powerUsage) {
        this.preocessIdList = processId;
        this.powerUsageList = powerUsage;
    }
}
