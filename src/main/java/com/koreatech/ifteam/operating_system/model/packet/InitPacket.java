package com.koreatech.ifteam.operating_system.model.packet;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import com.koreatech.ifteam.operating_system.model.CoreMode;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.List;

public class InitPacket {
    public List<UiProcess> processTimes = new LinkedList<UiProcess>();

    public CoreMode[] coreModes = new CoreMode[4];
    public int scheduleMethod;

    public InitPacket(ObservableList<UiProcess> processList, String[] modeList, int i) {
        for(i = 0; i<4; i++){
            switch (modeList[i]){
                case "P":
                    coreModes[i] = CoreMode.P;
                    break;
                case "E":
                    coreModes[i] = CoreMode.E;
                    break;
                case "NULL":
                    coreModes[i] = CoreMode.OFF;
                    break;

            }
        }
        this.processTimes = processList;
        this.scheduleMethod = i;

    }
}
