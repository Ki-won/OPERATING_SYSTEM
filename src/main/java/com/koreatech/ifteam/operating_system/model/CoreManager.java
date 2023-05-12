package com.koreatech.ifteam.operating_system.model;

import com.koreatech.ifteam.operating_system.model.packet.CorePacket;

import java.util.LinkedList;
import java.util.List;

public class CoreManager{
    private static CoreManager instance = new CoreManager(); // singleton
    private CoreManager(){
        System.out.println("CoreManger()");
    }

    public static CoreManager getInstance() {
        return instance;
    }
    
    // Variables

    private int coreNum = 0; // 프로세서 개수
    private List<Core> coreList = new LinkedList<Core>();
    private boolean flag[] = new boolean[4]; // 각 프로세서가 사용 가능한지 판별하는 flag

    

    public void clockUpdate() {
        manageCoreState();
    }
    
    // Getter

    public int getCoreNum() { // 코어 개수 getter
        return coreNum;
    }

    public Core getCore(int index) { // 코어 getter
        return coreList.get(index);
    }

    public CorePacket getPackets() { // 패킷들 getter
        int[] prcoessIdList = new int[4];
        float[] powerUsageList = new float[4];
        for (int i = 0; i < 4; ++i) {
            Core getCore = coreList.get(i);
            if(getCore == null){
                prcoessIdList[i] = -1;
                powerUsageList[i] = 0f;
            }else {
                prcoessIdList[i] = getCore.getId();
                powerUsageList[i] = getCore.getPowerUsage();
            }
        }
        return new CorePacket(prcoessIdList, powerUsageList);
    }

    // Setter

    // Functions

    public void initCore(CoreMode[] modes) { // 프로세서 초기화
        for (int i = 0; i < 4; ++i) {
            flag[i] = false;
            if (modes[i] == CoreMode.OFF) {
                coreList.add(null);
            }else{
                ++coreNum;
                coreList.add(new Core(i + 1, modes[i]));
            }
        }

        System.out.println("[info] processor(Core) Num: " + coreNum);
    }

    public boolean isDoneCore() { // 모든 코어가 작동을 멈췄는지 확인 == 이거 selectable 함수랑 쓰임이 같음, 나중에 삭제할 것
        for (int i = 0; i < 4; ++i) {
            if (coreList.get(i) == null)
                continue;
            if (flag[i]) {
                return true;
            }
        }
        return false;
    } 

    public boolean allocateAt(Process process){ // 프로세스 할당
        Integer selectedIndex = selectCore();
        if(selectedIndex == null) return false;
        flag[selectedIndex] = true;
        coreList.get(selectedIndex).allocate(process);
        return true;
    }

    public boolean allocateAtForCustom(Process process, int index){ // 프로세스 할당
        flag[index] = true;
        coreList.get(index).allocate(process);
        return true;
    }

    public void overwriteAt(int selectedIndex, Process process) { // 현재 코어의 작동을 중지시키지 않고, 프로세스 교체(덮어쓰기 개념)
        coreList.get(selectedIndex).allocate(process);
    }
    
    public void removeProcess(int index){ // 프로세스 삭제
        flag[index] = false;
        coreList.get(index).allocate(null);
    }

    private void manageCoreState() {
        for (int i = 0; i < coreList.size(); i++) {
            Core core = coreList.get(i);
            if (core != null) { // null 값 체크
                core.identifyStatus();
            }
        }
    }

    public boolean operating(int index){ // 프로세스 처리
        Core getCore = coreList.get(index);
        if (getCore == null) {
            System.out.println("Error: invalid core Index of " + index + " in operating(index)");
            return false;
        }
        getCore.operate();
        if(getCore.isComplete()){  // 프로세스 처리 종료
            return true;
        }
        return false;
    }

    private Integer selectCore(){ // 프로세서 선택
        for (int i = 0; i < 4; ++i) {
            if (coreList.get(i) == null)
                continue;
            if(flag[i] == false){
                return i;
            }
        }
        return null;
    }

    private int selectPCore(){ // P코어 선택
        for (int i = 0; i < 4; ++i) {
            if (coreList.get(i) == null)
                continue;
            if(flag[i] == false && coreList.get(i).getMode() == CoreMode.P){
                return i;
            }
        }
        return -1;
    }
    private int selectECore(){ // 프로세서 선택
        for (int i = 0; i < 4; ++i) {
            if (coreList.get(i) == null)
                continue;
            if(flag[i] == false && coreList.get(i).getMode() == CoreMode.E){
                return i;
            }
        }
        return -1;
    }

    public int getPCorePosition(){return selectPCore();} // 할당하기전 P코어의 위치를 받음

    public int getECorePosition(){return selectECore();} // 할당하기전 E코어의 위치를 받음
    public boolean selectable(){ // 프로세서가 선택 가능한지 = 사용 가능한 프로세서가 있는지
        return selectCore() != null;
    } // 전체 코어에 쓸 수 있는 코어가 있는지 확인

    public boolean selectablePCore(){ // 프로세서가 선택 가능한지 = 사용 가능한 프로세서가 있는지
        return selectPCore() != -1;
    } // P 코어중 쓸 수 있는 코어가 있는지 확인
    public boolean selectableECore(){ // 프로세서가 선택 가능한지 = 사용 가능한 프로세서가 있는지
        return selectECore() != -1;
    } // E 코어중 쓸 수 있는 코어가 있는지 확인

    public void printInfo() {
        System.out.println(" #");
        for (int i = 0; i < 4; ++i) {
            if (coreList.get(i) == null)
                continue;
            Core getCore = coreList.get(i);
            int coreId = getCore.getId();
            String processName = "";
            int currTime = 0;
            if (getCore.getProcess() != null) {
                processName = getCore.getProcess().getName();
                currTime = getCore.getProcess().getRemainTime();
                System.out.println(coreId + "코어 현황: " + processName + ", " + currTime + "초 남음");
            } else {
                System.out.println(coreId + "코어 현황: " + getCore.getProcess() + ", " + currTime + "초 남음");
            }
            
        }
        
        printPowerUsage();
    }
    
    public void printPowerUsage() {
        System.out.println();
        for (int i = 0; i < 4; ++i) {
            if (coreList.get(i) == null)
                continue;
            Core getCore = coreList.get(i);
            System.out.println(getCore.getId()+"코어 전력 사용량: " + getCore.getPowerUsage()+" W");
        }
    }

    
}
