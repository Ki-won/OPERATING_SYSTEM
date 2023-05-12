package com.koreatech.ifteam.operating_system.model;

import com.koreatech.ifteam.operating_system.model.scheduling.FCFS;

public class projectMain{
    public static void main(String[] args){
        ProcessManager.getInstance().addProcess("p0", 0, 3);
        ProcessManager.getInstance().addProcess("p1", 1, 7);
        ProcessManager.getInstance().addProcess("p2",3, 2);
        ProcessManager.getInstance().addProcess("p3",5, 5);
        ProcessManager.getInstance().addProcess("p4",6, 3); // 교재 정보

        /*ProcessManager.getInstance().addProcess(0, 3);
        ProcessManager.getInstance().addProcess(3, 2);
        ProcessManager.getInstance().addProcess(1, 7);
        ProcessManager.getInstance().addProcess(6, 3);
        ProcessManager.getInstance().addProcess(5, 5); // 위 정보 순서만 바꿈*/

        ProcessManager.getInstance().printInfo();

        //CoreMode modes[] = {CoreMode.P, CoreMode.E, CoreMode.P, CoreMode.E};
        CoreMode modes[] = {CoreMode.P, CoreMode.E, CoreMode.OFF, CoreMode.OFF};
        CoreManager.getInstance().initCore(modes);

        ScheduleManager.getInstance().setMethod(FCFS.getInstance(), "CFS");

        System.out.println("입력된 알고리즘");

        SyncManager.getInstance().run();

        ProcessManager.getInstance().printResult();
        CoreManager.getInstance().printPowerUsage();
    }
}