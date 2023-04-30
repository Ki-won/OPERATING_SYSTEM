package com.koreatech.ifteam.operating_system.UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProcessInputUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        // 가장 바깥쪽 레이아웃인 BorderPane을 생성합니다.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // 1. 가장 왼쪽 상단의 Process 입력창을 담는 VBox를 생성합니다.
        VBox processInputBox = new VBox(10);
        processInputBox.setPadding(new Insets(10));
        processInputBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        // 프로세스의 BT을 입력받는 TextField와 라벨을 생성합니다.
        Label btLabel = new Label("BT:");
        TextField btTextField = new TextField();
        HBox btHBox = new HBox(10, btLabel, btTextField);

        // 프로세스의 AT을 입력받는 TextField와 라벨을 생성합니다.
        Label atLabel = new Label("AT:");
        TextField atTextField = new TextField();
        HBox atHBox = new HBox(10, atLabel, atTextField);

        // 프로세스를 추가하는 Button을 생성합니다.
        Button addButton = new Button("Add Process");

        // VBox에 생성한 요소들을 추가합니다.
        processInputBox.getChildren().addAll(btHBox, atHBox, addButton);

        // VBox를 왼쪽 영역에 배치합니다.
        root.setLeft(processInputBox);

        // 2. Process 입력창 오른쪽의 ProcessList를 보여주는 창을 담는 VBox를 생성합니다.
        VBox processListBox = new VBox(10);
        processListBox.setPadding(new Insets(10));
        processListBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        // 표의 컬럼들을 생성합니다.
        TableColumn<Process, Integer> btColumn = new TableColumn<>("BT");
        TableColumn<Process, Integer> atColumn = new TableColumn<>("AT");
        TableColumn<Process, Integer> aatColumn = new TableColumn<>("AAT");
        TableColumn<Process, Integer> ttaColumn = new TableColumn<>("TTA");

        // 표를 생성하고 컬럼들을 추가합니다.
        TableView<Process> processTable = new TableView<>();
        processTable.getColumns().addAll(btColumn, atColumn, aatColumn, ttaColumn);

        // VBox에 생성한 요소들을 추가합니다.
        processListBox.getChildren().add(processTable);

        // VBox를 가용 영역에 배치합니다.
        root.setCenter(processListBox);
        // 3. 하단에 프로세스를 선택하는 창을 담는 GridPane을 생성합니다.
        GridPane processSelectionPane = new GridPane();
        processSelectionPane.setPadding(new Insets(10));
        processSelectionPane.setHgap(10);
        processSelectionPane.setVgap(10);
        processSelectionPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        // 여러 옵션 중 한 가지를 선택할 수 있는 ScrollPane을 생성합니다.
        ScrollPane optionsScrollPane = new ScrollPane();
        optionsScrollPane.setPrefHeight(100);
        optionsScrollPane.setPadding(new Insets(5));

        // RUN 버튼을 생성합니다.
        Button runButton = new Button("RUN");

        // GridPane에 생성한 요소들을 추가합니다.
        processSelectionPane.add(optionsScrollPane, 0, 0);
        processSelectionPane.add(runButton, 1, 0);

        // GridPane을 아래쪽 영역에 배치합니다.
        root.setBottom(processSelectionPane);

        // 윈도우를 생성하고 BorderPane을 씬으로 설정합니다.
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Process 클래스를 정의합니다.
    public static class Process {
        private int bt;
        private int at;
        private int aat;
        private int tta;

        public Process(int bt, int at) {
            this.bt = bt;
            this.at = at;
        }

        public int getBt() {
            return bt;
        }

        public void setBt(int bt) {
            this.bt = bt;
        }

        public int getAt() {
            return at;
        }

        public void setAt(int at) {
            this.at = at;
        }

        public int getAat() {
            return aat;
        }

        public void setAat(int aat) {
            this.aat = aat;
        }

        public int getTta() {
            return tta;
        }

        public void setTta(int tta) {
            this.tta = tta;
        }
    }
}