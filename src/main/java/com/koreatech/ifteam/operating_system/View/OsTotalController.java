package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import com.koreatech.ifteam.operating_system.model.CoreManager;
import com.koreatech.ifteam.operating_system.model.ProcessManager;
import com.koreatech.ifteam.operating_system.model.ScheduleManager;
import com.koreatech.ifteam.operating_system.model.UIController;
import com.koreatech.ifteam.operating_system.model.packet.CoreDataToUI;
import com.koreatech.ifteam.operating_system.model.packet.CorePacket;
import com.koreatech.ifteam.operating_system.model.packet.GanttPacket;
import com.koreatech.ifteam.operating_system.model.packet.InitPacket;
import com.koreatech.ifteam.operating_system.model.packet.ProcessPacket;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OsTotalController {
    String algorithmChoice = "";
    int choiceNum = 0;
    private final String[] modeList = new String[]{"NULL","NULL","NULL","NULL"};
    int [] saveResult = new int[6];
    private final ObservableList<UiProcess> processList = FXCollections.observableArrayList();
    private static ObservableList<ProcessPacket> resultList = FXCollections.observableArrayList();
    private static ObservableList<CorePacket> CoreList = FXCollections.observableArrayList();
    private static ObservableList<Float> CorePower = FXCollections.observableArrayList();

    private static ObservableList<CoreDataToUI> ganttData_1 = FXCollections.observableArrayList();

    private static ObservableList<CoreDataToUI> ganttData_2 = FXCollections.observableArrayList();

    private static ObservableList<CoreDataToUI> ganttData_3 = FXCollections.observableArrayList();

    private static ObservableList<CoreDataToUI> ganttData_4 = FXCollections.observableArrayList();
    ProcessPacket processPackets = new ProcessPacket(0, 0, 0, 0, 0, 0);

    //두개의 table
    @FXML
    private TableView<UiProcess> inputTable;
    //입력 테이블
    @FXML
    private TableColumn<UiProcess, String> nameColumn;
    @FXML
    private TableColumn<UiProcess, Integer> atColumn;
    @FXML
    private TableColumn<UiProcess, Integer> btColumn;
    @FXML
    private TableView<ProcessPacket> outputTable;
    // 출력 테이블
    @FXML
    private TableColumn<ProcessPacket, Integer> output_name;
    @FXML
    private TableColumn<ProcessPacket, Integer> output_at;
    @FXML
    private TableColumn<ProcessPacket, Integer> output_bt;
    @FXML
    private TableColumn<ProcessPacket, Integer> output_wt;
    @FXML
    private TableColumn<ProcessPacket, Integer> output_tt;
    @FXML
    private TableColumn<ProcessPacket, Integer> output_ntt;
    //process input받는 곳
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField atTextField;
    @FXML
    private TextField btTextField;
    @FXML
    private TextField timeQuantum;
    //전력양 표시하는 테이블
    @FXML
    public   TextField core1_power;  // Core1 정보를 표시할 TextField
    @FXML
    private  TextField core2_power; // Core2 정보를 표시할 TextField
    @FXML
    private  TextField core3_power; // Core3 정보를 표시할 TextField
    @FXML
    private  TextField core4_power; // Core4 정보를 표시할 TextField
    @FXML
    private  TextField total_power;

    //core 선택 버튼 토글들
    @FXML
    private ToggleGroup core1ToggleGroup;
    @FXML
    private ToggleGroup core2ToggleGroup;
    @FXML
    private ToggleGroup core3ToggleGroup;
    @FXML
    private ToggleGroup core4ToggleGroup;
    //알고리즘 종류 선택
    @FXML
    private ChoiceBox<String> algorithmChoiceBox; // ChoiceBox 선언

    //간트차트 그래프 줄
    @FXML
    private Canvas gantCore1;
    @FXML
    private Canvas gantCore2;
    @FXML
    private Canvas gantCore3;
    @FXML
    private Canvas gantCore4;

    static int canvas_X = 0;
    static int canvas_X2 = 0;
    static int canvas_X3 = 0;
    static int canvas_X4 = 0;


    public void initialize() {

        for(int i = 0; i < 4; i++){
            CorePower.add(0.0F);
        }
        algorithmChoiceBox.getItems().addAll("FCFS", "RR", "SPN", "SRTN", "HRRN", "CUSTOM"); // "FCFS"와 "RR" 추가

        //전력양 초기화

        core1_power.setText(" ");
        core2_power.setText(" ");
        core3_power.setText(" ");
        core4_power.setText(" ");
        total_power.setText(" ");

        //inputlist 컬럼을 수정하는 부분
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        atColumn.setCellValueFactory(new PropertyValueFactory<>("AT"));
        btColumn.setCellValueFactory(new PropertyValueFactory<>("BT"));
        //outputlist 컬럼 수정하는 부분

        output_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        output_at.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        output_bt.setCellValueFactory(new PropertyValueFactory<>("operateTime"));
        output_wt.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        output_tt.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        output_ntt.setCellValueFactory(new PropertyValueFactory<>("normalizedTT"));
        // 셀 팩토리 설정
        inputTable.setItems(processList);
        outputTable.setItems(resultList);
        outputTable.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends ProcessPacket> observable, ProcessPacket oldValue, ProcessPacket newValue) -> {
                    System.out.println("Selected item: " + newValue);
                    // 수행할 처리
                });

        //알고리즘 선택버튼
        algorithmChoiceBox.setOnAction(e -> {
            String selectedAlgorithm = algorithmChoiceBox.getValue();
            algorithmChoice = selectedAlgorithm;
            switch (algorithmChoice) {
                case "FCFS":
                    choiceNum = 0;
                    break;
                case "RR":
                    choiceNum = 1;
                    break;
                case "SPN":
                    choiceNum = 2;
                    break;
                case "SRTN":
                    choiceNum = 3;
                    break;
                case "HRRN":
                    choiceNum = 4;
                    break;
                case "CUSTOM":
                    choiceNum = 5;
                    break;
            }
            System.out.println("Selected algorithm: " + selectedAlgorithm + choiceNum);
        });

        //토글 버튼 그룹 세팅
        core1ToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("Core1: NULL");
            } else {
                RadioButton selectedRadio = (RadioButton) newValue;
                System.out.println("Core1: " + selectedRadio.getText());
                modeList[0] = selectedRadio.getText();
            }
        });

        core2ToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("Core2: NULL");
            } else {
                RadioButton selectedRadio = (RadioButton) newValue;
                System.out.println("Core2: " + selectedRadio.getText());
                modeList[1] = selectedRadio.getText();
            }
        });
        core3ToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("Core3: NULL");
            } else {
                RadioButton selectedRadio = (RadioButton) newValue;
                System.out.println("Core3: " + selectedRadio.getText());
                modeList[2] = selectedRadio.getText();
            }
        });
        core4ToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("Core4: NULL");
            } else {
                RadioButton selectedRadio = (RadioButton) newValue;
                System.out.println("Core4: " + selectedRadio.getText());
                modeList[3] = selectedRadio.getText();
            }
        });

    }
    @FXML
    private void onAddButtonClick(ActionEvent event) {
        String name = nameTextField.getText();
        int at = Integer.parseInt(atTextField.getText());
        int bt = Integer.parseInt(btTextField.getText());

        UiProcess process = new UiProcess(at, bt, Integer.parseInt(name)); // 변경
        processList.add(process);
        nameTextField.clear();
        atTextField.clear();
        btTextField.clear();
    }
    @FXML
    private void onResetButtonClick(ActionEvent event) {
        GraphicsContext gc = gantCore1.getGraphicsContext2D();
        processList.clear();
        resultList.clear();
        gc.clearRect(0, 0, gantCore1.getWidth(), gantCore1.getHeight());
        gc.clearRect(0, 0, gantCore2.getWidth(), gantCore2.getHeight());
        gc.clearRect(0, 0, gantCore3.getWidth(), gantCore3.getHeight());
        gc.clearRect(0, 0, gantCore4.getWidth(), gantCore4.getHeight());

    }
    @FXML
    private void onStartButtonClick(ActionEvent actionEvent) {
        int quantum = 0;
        float total = 0.0f;
        if (timeQuantum.getText() != "") {
            quantum = Integer.parseInt(timeQuantum.getText());
            System.out.println("setTimeQuantum" + quantum);
        }
        ScheduleManager.getInstance().setValue(quantum);
        System.out.println("before, method index: " + choiceNum);
        UIController.getInstance().initHandle(new InitPacket(processList, modeList, choiceNum));

        UIController.getInstance().StateHandle(0);

        ProcessManager.getInstance().printResult();
        CoreManager.getInstance().printPowerUsage();
        outputTable.refresh();

        for (int i = 0; i< 4; ++i){
            total += CorePower.get(i);
            if ( i == 0){
                core1_power.setText(String.valueOf(CorePower.get(i)));
                core1_power.setEditable(false);
            }
            if ( i == 1){
                core2_power.setText(String.valueOf(CorePower.get(i)));
                core2_power.setEditable(false);
            }
            if ( i == 2){
                core3_power.setText(String.valueOf(CorePower.get(i)));
                core3_power.setEditable(false);
            }
            if ( i == 3){
                core4_power.setText(String.valueOf(CorePower.get(i)));
                core4_power.setEditable(false);
            }
        }

        total_power.setText(String.valueOf(total));
        total_power.setEditable(false);
        GraphicsContext gant_core1 = gantCore1.getGraphicsContext2D();
        GraphicsContext gant_core2 = gantCore2.getGraphicsContext2D();
        GraphicsContext gant_core3 = gantCore3.getGraphicsContext2D();
        GraphicsContext gant_core4 = gantCore4.getGraphicsContext2D();

        Font font = Font.font(20);


        int canvas_Y = 5;
        int height = 40;
        Color[] colorList = new Color[]{Color.RED, Color.BLUEVIOLET, Color.GREEN, Color.PURPLE, Color.GOLDENROD,
                Color.BLACK, Color.BISQUE, Color.DARKVIOLET, Color.INDIGO, Color.DARKGREEN, Color.FORESTGREEN,
        Color.SPRINGGREEN,Color.TOMATO};

        for (int i = 0; i < ganttData_1.size(); i++) {
            canvas_X += ganttData_1.get(i).startTime*20;
            gant_core1.setFill(colorList[ganttData_1.get(i).getProcessId()]);
            gant_core1.setFont(font);
            int width = ganttData_1.get(i).getRunTime()*20;
            gant_core1.fillRect(canvas_X,canvas_Y, width, height);
            String text = String.valueOf(ganttData_1.get(i).getProcessId());
            double textWidth = gant_core1.getFont().getSize() * text.length();
            double textHeight = gant_core1.getFont().getSize();
            gant_core1.setFill(Color.WHITE);
            gant_core1.fillText(text, canvas_X + (width - textWidth) / 2, canvas_Y + (height + textHeight) / 2);
            canvas_X = 0;
        }
        for (int i = 0; i < ganttData_2.size(); i++) {
            canvas_X2 += ganttData_2.get(i).startTime*20;
            gant_core2.setFill(colorList[ganttData_2.get(i).getProcessId()]);
            gant_core2.setFont(font);
            int width = ganttData_2.get(i).getRunTime()*20;
            gant_core2.fillRect(canvas_X2,canvas_Y, width, height);
            String text = String.valueOf(ganttData_2.get(i).getProcessId());
            double textWidth = gant_core2.getFont().getSize() * text.length();
            double textHeight = gant_core2.getFont().getSize();
            gant_core2.setFill(Color.WHITE);
            gant_core2.fillText(text, canvas_X2 + (width - textWidth) / 2, canvas_Y + (height + textHeight) / 2);
            canvas_X2 = 0;
        }
        for (int i = 0; i < ganttData_3.size(); i++) {
            canvas_X3 += ganttData_3.get(i).startTime*20;
            gant_core3.setFill(colorList[ganttData_3.get(i).getProcessId()]);
            gant_core3.setFont(font);
            int width = ganttData_3.get(i).getRunTime()*20;
            gant_core3.fillRect(canvas_X3,canvas_Y, width, height);
            String text = String.valueOf(ganttData_3.get(i).getProcessId());
            double textWidth = gant_core3.getFont().getSize() * text.length();
            double textHeight = gant_core3.getFont().getSize();
            gant_core3.setFill(Color.WHITE);
            gant_core3.fillText(text, canvas_X3 + (width - textWidth) / 2, canvas_Y + (height + textHeight) / 2);
        }
        for (int i = 0; i < ganttData_3.size(); i++) {
            canvas_X4 += ganttData_4.get(i).startTime*20;
            gant_core4.setFill(colorList[ganttData_4.get(i).getProcessId()]);
            gant_core4.setFont(font);
            int width = ganttData_3.get(i).getRunTime()*20;
            gant_core4.fillRect(canvas_X4,canvas_Y, width, height);
            String text = String.valueOf(ganttData_3.get(i).getProcessId());
            double textWidth = gant_core4.getFont().getSize() * text.length();
            double textHeight = gant_core4.getFont().getSize();
            gant_core4.setFill(Color.WHITE);
            gant_core4.fillText(text, canvas_X4 + (width - textWidth) / 2 +3, canvas_Y + (height + textHeight) / 2);
        }
    }

    //프로세싱 완료 결과값 받는 함수
    public static void resultHandle(ProcessPacket processPacket) {
        resultList.add(processPacket);

    }


    public static void coreStatusHandle(CorePacket corePacket) {
            for(int i = 0; i < 4; i++){
                    CorePower.set(i, corePacket.powerUsageList[i]);
            }
    }

    public static void ganttStatusHandle(GanttPacket ganttPacket) {
        System.out.println("Core1");
        for (int i = 0; i<4; i++){
            if(i == 0) {
                for (int j = 0; j < ganttPacket.getSize(i); j++)
                    ganttData_1.add(ganttPacket.getData(i, j));
            }
            else if(i == 1) {
                for (int j = 0; j < ganttPacket.getSize(i); j++)
                    ganttData_2.add(ganttPacket.getData(i, j));
            }
            else if(i == 2) {
                for (int j = 0; j < ganttPacket.getSize(i); j++)
                    ganttData_3.add(ganttPacket.getData(i, j));
            }
            else if(i == 3) {
                for (int j = 0; j < ganttPacket.getSize(i); j++)
                    ganttData_4.add(ganttPacket.getData(i, j));
            }


        }
    }
}


