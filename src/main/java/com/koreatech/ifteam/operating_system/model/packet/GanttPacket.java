package com.koreatech.ifteam.operating_system.model.packet;

import java.util.LinkedList;
import java.util.List;

public class GanttPacket {
    private List<CoreDataToUI> core_1;
    private List<CoreDataToUI> core_2;
    private List<CoreDataToUI> core_3;
    private List<CoreDataToUI> core_4;

    public int totalTime;

    public GanttPacket() {
        core_1 = new LinkedList<CoreDataToUI>();
        core_2 = new LinkedList<CoreDataToUI>();
        core_3 = new LinkedList<CoreDataToUI>();
        core_4 = new LinkedList<CoreDataToUI>();
    }

    public void addCoreDataToUI(int index, CoreDataToUI coreData) {
        switch (index) {
            case 1:
                core_1.add(coreData);
                break;
            case 2:
                core_2.add(coreData);
                break;
            case 3:
                core_3.add(coreData);
                break;
            case 4:
                core_4.add(coreData);
                break;
            default:
                break;
        }
    }
    
    public CoreDataToUI getData(int index, int times) {
        switch (index) {
            case 0:
                if (core_1.isEmpty())
                    break;
                return core_1.get(times);
            case 1:
                if (core_2.isEmpty())
                    break;
                return core_2.get(times);
            case 2:
                if (core_3.isEmpty())
                    break;
                return core_3.get(times);
            case 3:
                if (core_4.isEmpty())
                    break;
                return core_4.get(times);
            default:
                break;
        }
        return null;
    }

    public Integer getSize(int index) {
        switch (index) {
            case 0:
                return core_1.size();
            case 1:
                return core_2.size();
            case 2:
                return core_3.size();
            case 3:
                return core_4.size();
            default:
                break;
        }
        return null;
    }
}
