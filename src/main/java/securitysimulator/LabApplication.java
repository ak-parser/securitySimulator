package securitysimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import securitysimulator.Models.Building;
import securitysimulator.Thread.*;

import java.io.IOException;

public class LabApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LabApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Security simulator!");
        stage.setScene(scene);
        stage.setOnCloseRequest((evt)->{
            LabController.killHandlerThread();
            LabController.killGeneratorThread();
        });
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

    /*public void stop(){
        System.out.println("Stop");
    }*/
}