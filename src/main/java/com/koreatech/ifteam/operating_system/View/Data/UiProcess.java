package com.koreatech.ifteam.operating_system.View.Data;
public class UiProcess {

    private int AT;
    private int BT;
    private int name;

    public UiProcess(int AT, int BT, int name) {
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

    public int getName() {
        return name;
    }
}

