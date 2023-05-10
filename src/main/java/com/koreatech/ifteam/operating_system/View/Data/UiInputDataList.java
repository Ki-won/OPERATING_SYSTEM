package com.koreatech.ifteam.operating_system.View.Data;

import java.util.ArrayList;
import java.util.List;

    public class UiInputDataList {

        public List<UiProcess> uiProcessList = new ArrayList<>();
        public List<UiCore> uiCoreList = new ArrayList<>();

        public  void  addCore(UiCore uiCore){
            uiCoreList.add(uiCore);
        }

        public void addProcess(UiProcess uiProcess) {
            uiProcessList.add(uiProcess);
        }

        public List<UiProcess> getProcessList() {
            return uiProcessList;
        }
    }
