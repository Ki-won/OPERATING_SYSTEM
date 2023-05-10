package com.koreatech.ifteam.operating_system.View.Data;
public class UiProcess {

    private int AT;
    private int BT;
    private String name;

    public UiProcess(int AT, int BT, String name) {
        this.AT = AT;
        this.BT = BT;
        this.name = name;
    }

    public int getAT() {
        return AT;
    }

    public int getBT() {
        return BT;
    }

    public String getName() {
        return name;
    }
}

