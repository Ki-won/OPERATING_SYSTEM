package com.koreatech.ifteam.operating_system.model;

import com.koreatech.ifteam.operating_system.model.packet.ProcessPacket;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ProcessManager { // Base Scheduling Model .....?
    private static ProcessManager instance = new ProcessManager(); // singleton
    private ProcessManager(){
        System.out.println("ProcessManager()");
    }

    public static ProcessManager getInstance() {
        return instance;
    }
    
    // Variables

    private PriorityQueue<Process> processQ = new PriorityQueue<>(); // 전체 프로세스 담는 큐 // chan: 이건 우선순위 큐 안해도 되지 않나
    private Queue<Process> readyQ = new LinkedList<Process>(); // 프로세스가 도착하여 기다리는 큐

    private List<Process> resultList = new LinkedList<Process>(); // 프로세스의 결과가 담긴 리스트
    private int nextId = 1;

    public void printInfo(){
        System.out.println("[info] processQ size: "+processQ.size());
        System.out.println("[info] readyQ size: "+readyQ.size());
    }

    public void addProcess(int arrivalTime, int burstTime) { // 프로세스 추가
        processQ.add(new Process(nextId++, arrivalTime, burstTime));
    }
    
    // Getter

    public int getProcessQueueSize(){ //레디 큐에 들어가지 않은 프로세스 수 계산
        return processQ.size();
    }

    public int getReadyQueueSize() { //레디 큐에 들어가지 않은 프로세스 수 계산
        return readyQ.size();
    }
    
    public Process peek_processQueue(){ // 우선순위 큐 잘 작동하는지 확인 위해 넣은것(나중에 삭제하자)
        return processQ.poll();
    }

    public Process poll_readyQueue() { // readyQueue에서 요소 추출
        Process getProcess = readyQ.poll();
        UIController.getInstance().readyProcessSend(getProcess, 1);
        return getProcess;
        
    }

    public Process peek_readyQueue() { // readyQueue에서 요소 접근
        return readyQ.peek();
    }

    public Process getMinBrustProcess() { // 짧은 버스트 타임을 가진 프로세스를 찾는 것
        Process minP = readyQ.poll();
        int lenQ = readyQ.size();
        for (int i = 0; i < lenQ; ++i) {
            Process tmp = readyQ.poll();
            if (minP.getBurstTime() > tmp.getBurstTime()) {
                readyQ.add(minP);
                minP = tmp;
            } else
                readyQ.add(tmp);
        }
        return minP;
    }

    public Process getMinRemainProcess() { // 짧은 실행 시간을 가진 프로세스를 찾음
        Process minP = readyQ.poll();
        int lenQ = readyQ.size();
        for (int i = 0; i < lenQ; ++i) {
            Process tmp = readyQ.poll();
            if (minP.getRemainTime() > tmp.getRemainTime()) {
                readyQ.add(minP);
                minP = tmp;
            } else
                readyQ.add(tmp);
        }
        return minP;
    }

    public Process getMinRRProcess(int cur_time) { // 큰 RR 을 찾는 것
        Process maxP = readyQ.poll();
        double maxRR = (cur_time - maxP.getArrivalTime()) / maxP.getBurstTime();
        int lenQ = readyQ.size();
        for (int i = 0; i < lenQ; ++i) {
            Process tmp = readyQ.poll();
            double tmpRR = (cur_time - tmp.getArrivalTime()) / tmp.getBurstTime();
            if (maxRR < tmpRR) {
                readyQ.add(maxP);
                maxP = tmp;
            } else
                readyQ.add(tmp);
        }
        return maxP;
    }

    public ProcessPacket[] getPackets() { // 패킷들 getter
        ProcessPacket[] processPackets = new ProcessPacket[processQ.size()];
        for (int i = 0; i < processQ.size(); ++i) {
            processPackets[i] = resultList.get(i).getPacket();
        }
        return processPackets;
    }
    
    // Setter

    public void push_readyQueue(Process process) { // readyQueue에 요소 삽입
        readyQ.add(process);
        UIController.getInstance().readyProcessSend(process, 0);
    }
    
    public void pushResultList(Process process) {
        resultList.add(process);
    };

    // Functions

    public boolean empty_readyQueue(){ // readyQueue가 비어있는지 검사
        return readyQ.isEmpty();
    }

    public void printResult(){
        for(int i = 0; i < resultList.size(); ++i){  //ui 구축되면 없어져도 되는거
            resultList.get(i).printInfo();
        }
    }

    public void saveToResultList(Process process) { // 처리 완료된 프로세스들은 ResultList에 저장
        process.setTurnaroundTime(SyncManager.getInstance().getClock() - process.getArrivalTime() + 1);
        process.setWaitTime(process.getTurnaroundTime() - process.getOperateTime());
        ProcessManager.getInstance().pushResultList(process);
        UIController.getInstance().resultSend(process);
    }

    public void clockUpdate(){ // Clock 주기
        Ready();
    }

    private void Ready(){ // 각 프로세스의 arrivalTime에, readyQueue로 이동시킴
        Process getProcess = processQ.peek();
        while (getProcess != null && getProcess.getArrivalTime() <= SyncManager.getInstance().getClock()) {
            Process pollProcess = processQ.poll();
            readyQ.add(pollProcess);
            getProcess = processQ.peek();
            UIController.getInstance().readyProcessSend(pollProcess, 0);
        }
    }
}
