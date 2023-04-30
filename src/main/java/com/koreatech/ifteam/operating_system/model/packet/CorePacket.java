package com.koreatech.ifteam.operating_system.model.packet;

public class CorePacket {
    public int preocessId;
    public float powerUsage;

    public CorePacket(int processId, float powerUsage) {
        this.preocessId = processId;
        this.powerUsage = powerUsage;
    }
}
