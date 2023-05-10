package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OsTotalController {
    //processinput보여주는 창
    @FXML
    private TableView<UiProcess> inputTable;
    @FXML
    private TableColumn<UiProcess, String> nameColumn;
    @FXML
    private TableColumn<UiProcess, Integer> atColumn;
    @FXML
    private TableColumn<UiProcess, Integer> btColumn;

    //process input받는 곳
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField atTextField;
    @FXML
    private TextField btTextField;

    @FXML
    private ToggleGroup core1ToggleGroup;
    @FXML
    private ToggleGroup core2ToggleGroup;
    @FXML
    private ToggleGroup core3ToggleGroup;
    @FXML
    private ToggleGroup core4ToggleGroup;


    private final ObservableList<UiProcess> processList = FXCollections.observableArrayList();

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        atColumn.setCellValueFactory(new PropertyValueFactory<>("AT"));
        btColumn.setCellValueFactory(new PropertyValueFactory<>("BT"));

        inputTable.setItems(processList);

        core1ToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == null) {
                    System.out.println("Core1: NULL");
                } else {
                    RadioButton selectedRadio = (RadioButton) newValue;
                    System.out.println("Core1: " + selectedRadio.getText());
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
                }
            }
        });

    }



    @FXML
    private void onAddButtonClick(ActionEvent event) {
        String name = nameTextField.getText();
        int at = Integer.parseInt(atTextField.getText());
        int bt = Integer.parseInt(btTextField.getText());

        UiProcess process = new UiProcess(at, bt, name); // 변경
        processList.add(process);

        nameTextField.clear();
        atTextField.clear();
        btTextField.clear();
    }

}


