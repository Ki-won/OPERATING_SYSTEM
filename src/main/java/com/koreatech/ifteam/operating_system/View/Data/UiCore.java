package com.koreatech.ifteam.operating_system.View.Data;

public class UiCore {
    private int id = 0; // 프로세서 ID, ex) 1코어, 2코어 ...
    private String mode = " "; // 프로세서 모드, E/P/OFF
    private String uiprocessname = " "; // 할당된 프로세스
    private float powerUsage = 0; // 전력 사용량?


    void setMode(String mode){
        this.mode = mode;
    }
    void setId(int id){
        this.id = id;
    }

    void setUiprocessname(String name){
        this.uiprocessname = name;
    }
    void setPowerUsage(float powerUsage){
        this.powerUsage = powerUsage;
    }

}
