module com.example.securitysimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens securitysimulator to javafx.fxml;
    exports securitysimulator;
    exports securitysimulator.Logger;
    opens securitysimulator.Logger to javafx.fxml;
}