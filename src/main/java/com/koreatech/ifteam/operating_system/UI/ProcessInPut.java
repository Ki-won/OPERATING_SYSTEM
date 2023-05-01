package com.koreatech.ifteam.operating_system.UI;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProcessInPut extends VBox {
    private final BorderPane ui = new BorderPane();
    private TextField btInput;
    private TextField atInput;
    private Button addButton;

    public ProcessInPut() {
        btInput = new TextField();
        atInput = new TextField();
        addButton = new Button("ADD");

        this.getChildren().addAll(btInput, atInput, addButton);
        this.setStyle("-fx-border-width: 1pt; -fx-border-color: black;");
    }

    public int getBT() {
        return Integer.parseInt(btInput.getText());
    }

    public int getAT() {
        return Integer.parseInt(atInput.getText());
    }

    public Button getAddButton() {
        return addButton;
    }
    public BorderPane getUI() {
        return ui;
    }
}
