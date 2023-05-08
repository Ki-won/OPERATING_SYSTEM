package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import com.koreatech.ifteam.operating_system.View.Data.UiProcessList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ProcessInPut {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ATField;

    @FXML
    private TextField BTField;

    @FXML
    private Button addButton;

    private UiProcessList processList = new UiProcessList();
    private ProcessInPutList processInPutList;

    public void setProcessList(UiProcessList processList) {
        this.processList = processList;
    }


    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            String name = nameField.getText();
            int AT = Integer.parseInt(ATField.getText());
            int BT = Integer.parseInt(BTField.getText());

            UiProcess process = new UiProcess(AT, BT, name);
            processList.addProcess(process);
            processInPutList.setProcessList(processList);


            nameField.clear();
            ATField.clear();
            BTField.clear();

        });
    }

}
