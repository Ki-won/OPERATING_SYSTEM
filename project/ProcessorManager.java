package project;

import java.util.LinkedList;
import java.util.List;

public class ProcessorManager{
    private static ProcessorManager instance = new ProcessorManager(); // singleton
    private ProcessorManager(){
        System.out.println("ProcessorManager()");
    }
    public static ProcessorManager getInstance(){
        return instance;
    }

    private int processorNum = 0; // 프로세서 개수
    private List<Processor> processorList = new LinkedList<Processor>();
    private boolean flag[]; // 각 프로세서가 사용 가능한지 판별하는 flag

    public void clockUpdate(){
        manageProcessorState();
    }

    public void initProcessor(CoreMode[] modes){ // 프로세서 초기화
        for(int i=0;i<4;++i){
            if(modes[i] != CoreMode.OFF){
                ++processorNum;
                processorList.add(new Processor(i + 1, modes[i]));
            }
        }
        flag = new boolean[processorNum];
        System.out.println("[info] processor(Core) Num: " + processorNum);
    }

    public int getProcessorNum(){ 
        return processorNum;
    }

    public Processor getProcessor(int index){ // 프로세서 getter
        return processorList.get(index);
    }
    
    public boolean allocateAt(Process process){ // 프로세스 할당 // void 
        Integer selectedIndex = selectProcessor();
        if(selectedIndex == null) return false;
        flag[selectedIndex] = true;
        processorList.get(selectedIndex).allocate(process);
        return true;
    }

    public void removeProcess(int index){ // 프로세스 삭제
        flag[index] = false;
        processorList.get(index).allocate(null);
    }

    private void manageProcessorState(){ // 프로세서 상태 관리
        for(int i = 0; i <processorNum; ++i){
            if(flag[i] == true){ // 프로세서가 가동 상태일 때만  // 조건문 x 
                Processor getProcessor = processorList.get(i); 
                // 프로세서의 마지막 가동 시간과 현재 시간이 1보다 많이 차이나면, 미가동 상태로 판단, 해당 프로세서를 휴면 상태로 전환
                if(SyncManager.getInstance().getTime() - getProcessor.getLastOperateTime() > 1){ 
                    getProcessor.sleep(); 
                }
            }
        }
    }

    public boolean operating(int index){ // 프로세스 처리
        Processor getProcessor = processorList.get(index);
        getProcessor.operate();
        if(getProcessor.isComplete()){  // 프로세스 처리 종료
            flag[index] = false;
            return true;
        }
        return false;
    }

    private Integer selectProcessor(){ // 프로세서 선택
        for(int i=0; i < processorNum; ++i){
            if(flag[i] == false){
                return i;
            }
        }
        return null;
    }

    public boolean selectable(){ // 프로세서가 선택 가능한지 = 사용 가능한 프로세서가 있는지
        return selectProcessor() != null;
    }

    public void printInfo(){
        for(int i = 0; i < processorNum; ++i){
            Processor getProcessor = processorList.get(i);
            int processorId = getProcessor.getId();
            String processId = "null";
            int currTime = 0;
            if(getProcessor.getProcess() != null){
                processId = getProcessor.getProcess().getId();
                currTime = getProcessor.getProcess().getCurrentTime();
            }
            System.out.println(processorId + "코어 현황: " + processId + ", " + currTime + "초 남음");
        }
    }
    
}
