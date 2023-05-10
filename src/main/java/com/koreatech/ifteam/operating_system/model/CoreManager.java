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
    private boolean flag[]; // 각 프로세서가 사용 가능한지 판별하는 flag

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

    public CorePacket[] getPackets() { // 패킷들 getter
        CorePacket[] corePackets = new CorePacket[coreNum];
        for (int i = 0; i < coreNum; ++i) {
            corePackets[i] = coreList.get(i).getPacket();
        }
        return corePackets;
    }

    // Setter

    // Functions

    public void initCore(CoreMode[] modes) { // 프로세서 초기화
        for (int i = 0; i < 4; ++i) {
            if (modes[i] != CoreMode.OFF) {
                ++coreNum;
                coreList.add(new Core(i + 1, modes[i]));
            }
        }
        flag = new boolean[coreNum];
        System.out.println("[info] processor(Core) Num: " + coreNum);
    }

    public boolean isDoneCore(){ // 모든 코어가 작동을 멈췄는지 확인 == 이거 selectable 함수랑 쓰임이 같음, 나중에 삭제할 것
        int count = 0;
        for(int i = 0; i < coreNum; ++i){
            if(!flag[i])
                ++count;
        }
        return count == coreNum;
    } 

    public boolean allocateAt(Process process){ // 프로세스 할당
        Integer selectedIndex = selectCore();
        if(selectedIndex == null) return false;
        flag[selectedIndex] = true;
        coreList.get(selectedIndex).allocate(process);
        return true;
    }

    public boolean allocateAtForCustom(Process process, int position){ // 프로세스 할당
        flag[position] = true;
        coreList.get(position).allocate(process);
        return true;
    }

    public void overwriteAt(int selectedIndex, Process process) { // 현재 코어의 작동을 중지시키지 않고, 프로세스 교체(덮어쓰기 개념)
        coreList.get(selectedIndex).allocate(process);
    }
    
    public void removeProcess(int index){ // 프로세스 삭제
        flag[index] = false;
        coreList.get(index).allocate(null);
    }

    private void manageCoreState(){ // 프로세서 상태 관리
        for(int i = 0; i <coreNum; ++i){
            coreList.get(i).identifyStatus(); // 프로세서의 마지막 가동 시간과 현재 시간이 1보다 많이 차이나면, 미가동 상태로 판단, 해당 프로세서를 휴면 상태로 전환
        }
    }

    public boolean operating(int index){ // 프로세스 처리
        Core getCore = coreList.get(index);
        getCore.operate();
        if(getCore.isComplete()){  // 프로세스 처리 종료
            return true;
        }
        return false;
    }

    private Integer selectCore(){ // 프로세서 선택
        for(int i=0; i < coreNum; ++i){
            if(flag[i] == false){
                return i;
            }
        }
        return null;
    }

    private int selectPCore(){ // P코어 선택
        for(int i=0; i < coreNum; ++i){
            if(flag[i] == false && coreList.get(i).getMode() == CoreMode.P){
                return i;
            }
        }
        return -1;
    }
    private int selectECore(){ // 프로세서 선택
        for(int i=0; i < coreNum; ++i){
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
        for (int i = 0; i < coreNum; ++i) {
            Core getCore = coreList.get(i);
            int coreId = getCore.getId();
            int processId = 0;
            int currTime = 0;
            if (getCore.getProcess() != null) {
                processId = getCore.getProcess().getId();
                currTime = getCore.getProcess().getRemainTime();
                System.out.println(coreId + "코어 현황: p" + processId + ", " + currTime + "초 남음");
            } else {
                System.out.println(coreId + "코어 현황: " + getCore.getProcess() + ", " + currTime + "초 남음");
            }
            
        }
        
        printPowerUsage();
    }
    
    public void printPowerUsage() {
        System.out.println();
        for (int i = 0; i < coreNum; ++i) {
            Core getCore = coreList.get(i);
            System.out.println(getCore.getId()+"코어 전력 사용량: " + getCore.getPowerUsage()+" W");
        }
    }

    
}
