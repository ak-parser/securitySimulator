module com.example.securitysimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.securitysimulator to javafx.fxml;
    exports com.example.securitysimulator;
}