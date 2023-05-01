package com.koreatech.ifteam.operating_system.UI;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class ProcessList {
    private TableView<Process> table;
    private Button addButton;
    private VBox layout;
    private final TableColumn<Process, ObservableList<Integer>> burstTimeColumn = new TableColumn<>("Burst Time");


    public ProcessList() {

        // 프로세스를 보여주는 TableView 생성
        table = new TableView<>();

        // 프로세스의 BT, AT, AAT를 표시하는 TableColumn 생성

        burstTimeColumn.setCellValueFactory(data -> {
            ObservableList<Integer> burstTimes = data.getValue().getBurstTime();
            return new ReadOnlyObjectWrapper<>(burstTimes);
        });
        TableColumn<Process, String> atCol = new TableColumn<>("AT");
        atCol.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asString());

        TableColumn<Process, String> aatCol = new TableColumn<>("AAT");
        aatCol.setCellValueFactory(cellData -> cellData.getValue().actualArrivalTimeProperty().asString());

        // TableColumn을 TableView에 추가
        int btCol = 0;

        table.getColumns().addAll(btCol, atCol, aatCol);

        // ADD 버튼 생성
        addButton = new Button("ADD");

        // VBox 생성 및 TableView와 ADD 버튼 추가
        layout = new VBox(10);
        layout.getChildren().addAll(new Text("Process List"), table, addButton);
        layout.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        // ADD 버튼 클릭 이벤트 핸들러 등록
        addButton.setOnAction(event -> {
            // 새로운 프로세스 추가
            Process newProcess = new Process();
            table.getItems().add(newProcess);
        });
    }

    // TableView에 있는 프로세스 목록을 반환하는 메소드
    public ObservableList<Process> getProcessList() {
        return table.getItems();
    }

    // VBox를 반환하는 메소드
    public VBox getLayout() {
        return layout;
    }
}
