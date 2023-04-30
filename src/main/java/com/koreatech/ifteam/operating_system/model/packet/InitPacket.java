package com.koreatech.ifteam.operating_system.model.packet;

import com.koreatech.ifteam.operating_system.model.CoreMode;
import com.koreatech.ifteam.operating_system.model.Pair;

import java.util.LinkedList;
import java.util.List;

public class InitPacket {
    public List<Pair> processTimes = new LinkedList<Pair>();

    public CoreMode[] coreModes = new CoreMode[4];
    public int scheduleMethod;
}
