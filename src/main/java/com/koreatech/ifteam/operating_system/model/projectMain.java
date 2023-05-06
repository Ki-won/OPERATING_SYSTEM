package com.koreatech.ifteam.operating_system.model;

import com.koreatech.ifteam.operating_system.model.scheduling.FCFS;
import com.koreatech.ifteam.operating_system.model.scheduling.HRRN;
import com.koreatech.ifteam.operating_system.model.scheduling.SPN;
import com.koreatech.ifteam.operating_system.model.scheduling.SRTN;
import com.koreatech.ifteam.operating_system.model.scheduling.CUSTOM;

public class projectMain{
    public static void main(String[] args){
        ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(5, 5);
        ProcessManager.getInstance().addProcess(6, 3); // 교재 정보

        /*ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(6, 3);
        ProcessManager.getInstance().addProcess(5, 5); // 위 정보 순서만 바꿈*/

        ProcessManager.getInstance().printInfo();

        //CoreMode modes[] = {CoreMode.P, CoreMode.E, CoreMode.P, CoreMode.E};
        CoreMode modes[] = {CoreMode.P, CoreMode.E, CoreMode.OFF, CoreMode.OFF};
        CoreManager.getInstance().initCore(modes);

        ScheduleManager.getInstance().setMethod(CUSTOM.getInstance(), "CUSTOM");

        System.out.println("입력된 알고리즘");

        SyncManager.getInstance().run();

        ProcessManager.getInstance().printResult();
        CoreManager.getInstance().printPowerUsage();
    }
}