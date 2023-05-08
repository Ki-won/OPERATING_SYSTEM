package com.koreatech.ifteam.operating_system.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RootController {

    @FXML
    private AnchorPane processInPutPane;

    @FXML
    private AnchorPane processInPutListPane;

    public RootController() {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/com/koreatech/ifteam/operating_system/process_in_put.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/com/koreatech/ifteam/operating_system/process_in_put_list.fxml"));

        try {
            processInPutPane = fxmlLoader1.load();
            processInPutListPane = fxmlLoader2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getProcessInPutPane() {
        return processInPutPane;
    }

    public AnchorPane getProcessInPutListPane() {
        return processInPutListPane;
    }
}

