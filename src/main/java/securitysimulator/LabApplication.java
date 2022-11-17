package securitysimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import securitysimulator.Models.Building;
import securitysimulator.Thread.*;

import java.io.IOException;

public class LabApplication extends Application {
    private ViolationGeneratorThread violationGeneratorThread;
    private ViolationHandlerThread violationHandlerThread;
    private Building building = new Building();

    private void createHandlerThread() {
        violationHandlerThread = new ViolationHandlerThread(building);
        violationHandlerThread.start();
    }

    private void createGeneratorThread() {
        violationGeneratorThread = new ViolationGeneratorThread(building);
        violationGeneratorThread.start();
    }

    private void killGeneratorThread() {
        violationGeneratorThread.kill();
    }

    public void onClickPlay() {
        createGeneratorThread();
    }

    public void onClickPause() {
        killGeneratorThread();
    }

    public void onClick() {}

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LabApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Security simulator!");
        stage.setScene(scene);
        stage.show();

        createHandlerThread();
    }
    public static void main(String[] args) {
        launch();
    }
}