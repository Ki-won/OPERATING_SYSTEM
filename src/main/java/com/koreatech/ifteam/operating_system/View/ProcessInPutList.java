package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import com.koreatech.ifteam.operating_system.View.Data.UiProcessList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProcessInPutList implements Initializable {
    @FXML
    private TableView<UiProcess> processTable;
    @FXML
    private TableColumn<Process, String> nameColumn;
    @FXML
    private TableColumn<Process, Integer> atColumn;
    @FXML
    private TableColumn<Process, Integer> btColumn;

    private UiProcessList processList;


    public void setprocesslist(UiProcessList processList){
        this.processList = processList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the columns.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        atColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        btColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        // Set up the data for the table.
        if (processList != null) {
            ObservableList<UiProcess> processData = FXCollections.observableArrayList((UiProcess) processList.getList());
            processTable.setItems(processData);
        }
    }



    public void setProcessList(UiProcessList processList) {
        this.processList = processList;
    }
}
