package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import com.koreatech.ifteam.operating_system.model.*;
import com.koreatech.ifteam.operating_system.model.packet.CorePacket;
import com.koreatech.ifteam.operating_system.model.packet.InitPacket;
import com.koreatech.ifteam.operating_system.model.packet.ProcessPacket;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OsTotalController {

    private static OsTotalController instance = new OsTotalController(); // singleton

    public static OsTotalController getInstance() {
        return instance;
    }
    int count = 0; // P값 개수 카운팅
    //processinput보여주는 창
    @FXML
    private TableView<UiProcess> inputTable;

    @FXML
    private TableView<ProcessPacket> outputTable;

    @FXML
    private TableColumn<UiProcess, String> nameColumn;
    @FXML
    private TableColumn<UiProcess, Integer> atColumn;
    @FXML
    private TableColumn<UiProcess, Integer> btColumn;
    // 입력 테이블
    @FXML
    private TableColumn<ProcessPacket, Integer> outputnameColumn;
    @FXML
    private TableColumn<ProcessPacket, Integer> outputatColumn;
    @FXML
    private TableColumn<ProcessPacket, Integer> outputbtColumn;
    @FXML
    private TableColumn<ProcessPacket, Integer> outputwtColumn;
    @FXML
    private TableColumn<ProcessPacket, Integer> outputttColumn;
    @FXML
    private TableColumn<ProcessPacket, Double> outputnttColumn;

    //process input받는 곳
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField atTextField;
    @FXML
    private TextField btTextField;

    //core 선택 버튼 토글들
    @FXML
    private ToggleGroup core1ToggleGroup;
    @FXML
    private ToggleGroup core2ToggleGroup;
    @FXML
    private ToggleGroup core3ToggleGroup;
    @FXML
    private ToggleGroup core4ToggleGroup;
    private String[] modeList = new String[4];
    private final ObservableList<UiProcess> processList = FXCollections.observableArrayList();
    private final ObservableList<ProcessPacket> resultList = FXCollections.observableArrayList(
            new ProcessPacket(3,4,5,6,7,8),
            new ProcessPacket(5,4,5,6,7,8),
    new ProcessPacket(3,4,5,6,7,8)
    );
    public void initialize() {
        //inputlist 컬럼을 수정하는 부분
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        atColumn.setCellValueFactory(new PropertyValueFactory<>("AT"));
        btColumn.setCellValueFactory(new PropertyValueFactory<>("BT"));
        //outputlist 컬럼 수정하는 부분
       // outputnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        outputatColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        outputbtColumn.setCellValueFactory(new PropertyValueFactory<>("operateTime"));
        outputwtColumn.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        outputttColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        outputnttColumn.setCellValueFactory(new PropertyValueFactory<>("normalizedTT"));

        inputTable.setItems(processList);
        outputTable.setItems(resultList);


        //토글 버튼 그룹 세팅
        core1ToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == null) {
                    System.out.println("Core1: NULL");
                } else {
                    RadioButton selectedRadio = (RadioButton) newValue;
                    System.out.println("Core1: " + selectedRadio.getText());
                    modeList[0] =  selectedRadio.getText();
                }
            }
        });
        core2ToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == null) {
                    System.out.println("Core2: NULL");
                } else {
                    RadioButton selectedRadio = (RadioButton) newValue;
                    System.out.println("Core2: " + selectedRadio.getText());
                    modeList[1] =  selectedRadio.getText();
                }
            }
        });
        core3ToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == null) {
                    System.out.println("Core3: NULL");
                } else {
                    RadioButton selectedRadio = (RadioButton) newValue;
                    System.out.println("Core3: " + selectedRadio.getText());
                    modeList[2] =  selectedRadio.getText();
                }
            }
        });
        core4ToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == null) {
                    System.out.println("Core4: NULL");
                } else {
                    RadioButton selectedRadio = (RadioButton) newValue;
                    System.out.println("Core4: " + selectedRadio.getText());
                    modeList[3] =  selectedRadio.getText();
                }
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
    private void onStartButtonClick(ActionEvent actionEvent) {
        UIController.getInstance().initHandle(new InitPacket(processList, modeList, 0));
        UIController.getInstance().StateHandle(0);
        ProcessManager.getInstance().printResult();
        CoreManager.getInstance().printPowerUsage();


    }
    //프로세싱 완료 결과값 받는 함수
    public void resultHandle(ProcessPacket processPacket){
        System.out.println("AT: "+processPacket.getArrivalTime());
        resultList.add(processPacket); // add the countype object to the resultList
        System.out.println(resultList.size());
        System.out.println("AT: "+resultList.get(0).getArrivalTime());

    }

    public void coreStatusHandle(CorePacket corePacket){
        System.out.println("Core1: "+corePacket.processIdList[0]+" power: "+corePacket.powerUsageList[0]);
        System.out.println("Core2: "+corePacket.processIdList[1]+" power: "+corePacket.powerUsageList[1]);
        System.out.println("Core3: "+corePacket.processIdList[2]+" power: "+corePacket.powerUsageList[2]);
        System.out.println("Core4: "+corePacket.processIdList[3]+" power: "+corePacket.powerUsageList[3]);
    }
}


