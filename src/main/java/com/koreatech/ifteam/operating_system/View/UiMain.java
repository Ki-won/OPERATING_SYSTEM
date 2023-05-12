package com.koreatech.ifteam.operating_system.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.AnchorPane;
public class UiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // FXML 파일을 로드합니다.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/koreatech/ifteam/operating_system/os_total_ui.fxml"));
        Parent root = loader.load();
        OsTotalController getController = loader.getController();
        // Scene을 생성합니다.
        Scene scene = new Scene(root);

        // Stage 설정을 합니다.
        primaryStage.setTitle("UI Example");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
