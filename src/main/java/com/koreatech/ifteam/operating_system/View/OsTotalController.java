package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class OsTotalController {
    @FXML
    private TableView<UiProcess> inputTable;
    @FXML
    private TableColumn<UiProcess, String> nameColumn;
    @FXML
    private TableColumn<UiProcess, Integer> atColumn;
    @FXML
    private TableColumn<UiProcess, Integer> btColumn;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField atTextField;
    @FXML
    private TextField btTextField;

    private ObservableList<UiProcess> processList = FXCollections.observableArrayList();

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        atColumn.setCellValueFactory(new PropertyValueFactory<>("AT"));
        btColumn.setCellValueFactory(new PropertyValueFactory<>("BT"));

        inputTable.setItems(processList);
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


