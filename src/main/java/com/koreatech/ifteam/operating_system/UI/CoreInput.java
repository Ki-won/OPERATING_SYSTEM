package com.koreatech.ifteam.operating_system.UI;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CoreInput extends VBox {
    private CheckBox[] coreCheckBoxes;
    private Button runButton;

    public CoreInput() {
        coreCheckBoxes = new CheckBox[4];
        coreCheckBoxes[0] = new CheckBox("Core 1");
        coreCheckBoxes[1] = new CheckBox("Core 2");
        coreCheckBoxes[2] = new CheckBox("Core 3");
        coreCheckBoxes[3] = new CheckBox("Core 4");

        ScrollPane scrollPane = new ScrollPane();
        VBox coreModeBox = new VBox();
        coreModeBox.getChildren().addAll(new CheckBox("P"), new CheckBox("E"), new CheckBox("NULL"));
        scrollPane.setContent(coreModeBox);
        scrollPane.setMaxHeight(100);

        runButton = new Button("RUN");

        HBox coreBox = new HBox();
        coreBox.getChildren().addAll(coreCheckBoxes);
        coreBox.setSpacing(10);

        this.getChildren().addAll(coreBox, scrollPane, runButton);
        this.setStyle("-fx-border-width: 1pt; -fx-border-color: black;");
    }

    public boolean[] getCoreStatus() {
        boolean[] coreStatus = new boolean[4];
        for (int i = 0; i < coreCheckBoxes.length; i++) {
            coreStatus[i] = coreCheckBoxes[i].isSelected();
        }
        return coreStatus;
    }

    public Button getRunButton() {
        return runButton;
    }
}

