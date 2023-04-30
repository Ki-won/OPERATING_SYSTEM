module com.koreatech.ifteam.operating_system {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
            
    opens com.koreatech.ifteam.operating_system to javafx.fxml;
    exports com.koreatech.ifteam.operating_system.model;
    exports com.koreatech.ifteam.operating_system.UI;
    opens com.koreatech.ifteam.operating_system.UI to javafx.fxml;
    exports com.koreatech.ifteam.operating_system.model.packet;
}