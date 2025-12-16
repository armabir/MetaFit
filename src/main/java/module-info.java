module com.example.metafit_v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.metafit_v1 to javafx.fxml;
    exports com.example.metafit_v1;
    exports com.example.metafit_v1.user_controllers;
    opens com.example.metafit_v1.user_controllers to javafx.fxml;
    exports com.example.metafit_v1.trainer_controllers;
    opens com.example.metafit_v1.trainer_controllers to javafx.fxml;
    exports com.example.metafit_v1.other_controllers;
    opens com.example.metafit_v1.other_controllers to javafx.fxml;
}