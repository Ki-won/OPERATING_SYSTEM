package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import com.koreatech.ifteam.operating_system.model.*;
import com.koreatech.ifteam.operating_system.model.Process;
import com.koreatech.ifteam.operating_system.model.packet.InitPacket;
import com.koreatech.ifteam.operating_system.model.packet.ProcessPacket;
import com.koreatech.ifteam.operating_system.model.scheduling.CUSTOM;
import com.koreatech.ifteam.operating_system.model.scheduling.FCFS;
import com.koreatech.ifteam.operating_system.model.scheduling.SPN;
import com.koreatech.ifteam.operating_system.model.scheduling.SRTN;
import com.koreatech.ifteam.operating_system.model.scheduling.RR;
import com.koreatech.ifteam.operating_system.model.scheduling.HRRN;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.security.auth.login.CredentialException;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

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
    private TableColumn<ProcessPacket, String> outputnameColumn;
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
    private final ObservableList<ProcessPacket> resultList = FXCollections.observableArrayList();

    public void initialize() {
        //inputlist 컬럼을 수정하는 부분
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        atColumn.setCellValueFactory(new PropertyValueFactory<>("AT"));
        btColumn.setCellValueFactory(new PropertyValueFactory<>("BT"));

        inputTable.setItems(processList);

        //outputlist 컬럼 수정하는 부분
        outputnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        outputatColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        outputbtColumn.setCellValueFactory(new PropertyValueFactory<>("operateTime"));
        outputwtColumn.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        outputttColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        //outputnttColumn.setCellValueFactory(new PropertyValueFactory<>("normalizeTT"));

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

        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Name을 추가해주세요!!");
            alert.showAndWait();
            return;
        }
        UiProcess process = new UiProcess(at, bt, name); // 변경
        ProcessManager.getInstance().addProcess(name, at, bt); // 프로세스 삽입
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
    public void resultHandle(ProcessPacket processPacket){
        resultList.add(processPacket);
    }
}


