package project;

import java.util.LinkedList;
import java.util.List;

public class CoreManager{
    private static CoreManager instance = new CoreManager(); // singleton
    private CoreManager(){
        System.out.println("CoreManger()");
    }
    public static CoreManager getInstance(){
        return instance;
    }

    private int coreNum = 0; // 프로세서 개수
    private List<Core> coreList = new LinkedList<Core>();
    private boolean flag[]; // 각 프로세서가 사용 가능한지 판별하는 flag

    public void clockUpdate(){
        manageCoreState();
    }

    public void initCore(CoreMode[] modes){ // 프로세서 초기화
        for(int i=0;i<4;++i){
            if(modes[i] != CoreMode.OFF){
                ++coreNum;
                coreList.add(new Core(i + 1, modes[i]));
            }
        }
        flag = new boolean[coreNum];
        System.out.println("[info] processor(Core) Num: " + coreNum);
    }

    public int getCoreNum(){ 
        return coreNum;
    }

    public Core getCore(int index){ // 프로세서 getter
        return coreList.get(index);
    }
    
    public boolean allocateAt(Process process){ // 프로세스 할당 // void 
        Integer selectedIndex = selectCore();
        if(selectedIndex == null) return false;
        flag[selectedIndex] = true;
        coreList.get(selectedIndex).allocate(process);
        return true;
    }

    public void removeProcess(int index){ // 프로세스 삭제
        flag[index] = false;
        coreList.get(index).allocate(null);
    }

    private void manageCoreState(){ // 프로세서 상태 관리
        for(int i = 0; i <coreNum; ++i){
            Core getCore = coreList.get(i); 
            // 프로세서의 마지막 가동 시간과 현재 시간이 1보다 많이 차이나면, 미가동 상태로 판단, 해당 프로세서를 휴면 상태로 전환
            if(SyncManager.getInstance().getTime() - getCore.getLastOperateTime() > 1){ 
                getCore.sleep(); 
            }
        }
    }

    public boolean operating(int index){ // 프로세스 처리
        Core getCore = coreList.get(index);
        getCore.operate();
        if(getCore.isComplete()){  // 프로세스 처리 종료
            flag[index] = false;
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

    public boolean selectable(){ // 프로세서가 선택 가능한지 = 사용 가능한 프로세서가 있는지
        return selectCore() != null;
    }

    public void printInfo(){
        for(int i = 0; i < coreNum; ++i){
            Core getCore = coreList.get(i);
            int coreId = getCore.getId();
            String processId = "null";
            int currTime = 0;
            if(getCore.getProcess() != null){
                processId = getCore.getProcess().getId();
                currTime = getCore.getProcess().getCurrentTime();
            }
            System.out.println(coreId + "코어 현황: " + processId + ", " + currTime + "초 남음");
        }
    }
    
}
