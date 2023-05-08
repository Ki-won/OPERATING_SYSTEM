package com.koreatech.ifteam.operating_system.View.Data;

import java.util.ArrayList;
import java.util.List;

public class UiProcessList {

    public List<UiProcess> uiProcessList = new ArrayList<>();

    public void addProcess(UiProcess uiProcess) {
        uiProcessList.add(uiProcess);
    }

    public List<UiProcess> getProcessList() {
        return uiProcessList;
    }

    public Object getList() {
        return uiProcessList;
    }
}
