package com.koreatech.ifteam.operating_system.UI;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UiMain {
    public void start(Stage primaryStage) throws Exception {
        // ProcessInput instance
        ProcessInPut processInput = new ProcessInPut();

        // CoreInput instance
        CoreInput coreInput = new CoreInput();

        // ProcessList instance
        ProcessList processList = new ProcessList();

        // ProcessResult instance
        ProcessResult processResult = new ProcessResult();

        // CoreGraph instance
        CoreGraph coreGraph = new CoreGraph();

        // Create root VBox and add all instances
        VBox root = new VBox();
        root.getChildren().addAll(processInput.getUI(), coreInput.getUI(),
                processList.getUI(), processResult.getUI(), coreGraph.getUI());

        // Add border to all instances
        addBorder(processInput.getUI(), "ProcessInput");
        addBorder(coreInput.getUI(), "CoreInput");
        addBorder(processList.getUI(), "ProcessList");
        addBorder(processResult.getUI(), "ProcessResult");
        addBorder(coreGraph.getUI(), "CoreGraph");

        // Set scene and show
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to add border to a node
    private void addBorder(Node node, String title) {
        node.setStyle("-fx-border-color: black; -fx-border-width: 1pt;");
        node.setPadding(new Insets(10));
        node.setPrefWidth(200);
        TitledPane titledPane = new TitledPane(title, node);
        titledPane.setCollapsible(false);
        titledPane.setPadding(new Insets(10));
        titledPane.setStyle("-fx-border-color: black; -fx-border-width: 1pt;");
        titledPane.setPrefWidth(220);
        titledPane.setMaxWidth(220);
        titledPane.setMinWidth(220);
        VBox.setMargin(titledPane, new Insets(10));
        VBox.setVgrow(titledPane, Priority.ALWAYS);
        VBox.setMargin(node, new Insets(0, 0, 10, 0));
    }
}

public class Process {
    private final String processName;
    private final IntegerProperty arrivalTime;
    private final ObservableList<Integer> burstTime;

    public Process(String processName, int arrivalTime, ObservableList<Integer> burstTime) {
        this.processName = processName;
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = burstTime;
    }

    public String getProcessName() {
        return processName;
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public IntegerProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public ObservableList<Integer> getBurstTime() {
        return burstTime;
    }

    public BooleanExpression actualArrivalTimeProperty() {
        BooleanExpression o = null;
        BooleanExpression o1 = o;
        return o1;
    }
}


