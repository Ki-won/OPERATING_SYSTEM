package com.koreatech.ifteam.operating_system.View;

import com.koreatech.ifteam.operating_system.View.Data.UiProcessList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        RootController rootController = new RootController();

        AnchorPane processInPutPane = rootController.getProcessInPutPane();
        AnchorPane processInPutListPane = rootController.getProcessInPutListPane();

        AnchorPane root = new AnchorPane(processInPutPane, processInPutListPane);
        root.setTopAnchor(processInPutPane, 0.0);
        root.setLeftAnchor(processInPutPane, 0.0);
        root.setBottomAnchor(processInPutListPane, 0.0);
        root.setRightAnchor(processInPutListPane, 0.0);



        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
