package project;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ProcessManager { // Base Scheduling Model .....? 
    private static ProcessManager instance = new ProcessManager(); // singleton
    private ProcessManager(){
        System.out.println("ProcessManager()");
    }
    public static ProcessManager getInstance(){
        return instance;
    }

    private PriorityQueue<Process> processQ = new PriorityQueue<>(); // 전체 프로세스 담는 큐
    private Queue<Process> readyQ = new LinkedList<Process>(); // 프로세스가 도착하여 기다리는 큐
    private int nextId = 1;

    public void printInfo(){
        System.out.println("[info] processQ size: "+processQ.size());
        System.out.println("[info] readyQ size: "+readyQ.size());
    }

    public void addProcess(int arrivalTime, int burstTime){ // 프로세스 추가
        processQ.add(new Process(nextId++, arrivalTime, burstTime));
    }

    public Process peek_processQueue(){ // 우선순위 큐 잘 작동하는지 확인 위해 넣은것(나중에 삭제하자)
        return processQ.poll();
    }
    public Process poll_readyQueue(){ // readyQueue에서 요소 추출 
        return readyQ.poll();
    }  // readyQueue에서 요소 추출

    public Process peek_readyQueue(){ // readyQueue에서 요소 접근
        return readyQ.peek();
    }

    public void push_readyQueue(Process process){ // readyQueue에 요소 삽입
        readyQ.add(process);
    }

    public boolean empty_readyQueue(){ // readyQueue가 비어있는지 검사
        return readyQ.isEmpty();
    }

    public void clockUpdate(){ // Clock 주기 
        Ready();
    }

    private void Ready(){ // 각 프로세스의 arrivalTime에, readyQueue로 이동시킴  
        Process getProcess = processQ.peek();
        while(getProcess != null && getProcess.getArrivalTime() <= SyncManager.getInstance().getTime()){
            readyQ.add(processQ.poll());
            getProcess = processQ.peek();
        }
    }
}
