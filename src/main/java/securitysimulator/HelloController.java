package securitysimulator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private ComboBox comboBox_violationType;
    @FXML
    private ComboBox comboBox_floor;
    @FXML
    private ComboBox comboBox_room;
    @FXML
    private ListView  list_datchyky;
    @FXML
    private TextArea list_log;
    @FXML
    private Spinner spinner_area1;
    @FXML
    private Spinner spinner_area2;
    @FXML
    private Spinner spinner_doors;
    @FXML
    private Spinner spinner_windows;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox_violationType.setItems(FXCollections.observableArrayList("Пожежа", "Вода", "Вторгнення", "Газ", "Рух"));
        comboBox_room.setItems(FXCollections.observableArrayList("Кімната 1"));
        comboBox_floor.setItems(FXCollections.observableArrayList("Поверх 1"));
        comboBox_violationType.getSelectionModel().selectFirst();
        comboBox_room.getSelectionModel().selectFirst();
        comboBox_floor.getSelectionModel().selectFirst();
        spinner_windows.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100)
        );
        spinner_doors.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100)
        );
        spinner_area1.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100)
        );
        spinner_area2.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100)
        );

    }




}