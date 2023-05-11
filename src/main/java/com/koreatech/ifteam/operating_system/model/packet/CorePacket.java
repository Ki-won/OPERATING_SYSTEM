package com.koreatech.ifteam.operating_system.model.packet;

public class CorePacket {
    public int[] preocessIdList;
    public float[] powerUsageList;
    public int clock;

    public CorePacket(int[] processId, float[] powerUsage, int clock) {
        this.preocessIdList = processId;
        this.powerUsageList = powerUsage;
        this.clock = clock;
    }
}
