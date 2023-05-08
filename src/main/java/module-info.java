module com.koreatech.ifteam.operating_system {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
            
    opens com.koreatech.ifteam.operating_system to javafx.fxml;
    exports com.koreatech.ifteam.operating_system.model;
    exports com.koreatech.ifteam.operating_system.View;
    opens com.koreatech.ifteam.operating_system.View to javafx.fxml;
    exports com.koreatech.ifteam.operating_system.model.packet;
    exports com.koreatech.ifteam.operating_system.Controller;
    exports com.koreatech.ifteam.operating_system.View.Data;
    opens com.koreatech.ifteam.operating_system.View.Data to javafx.fxml;
}