package securitysimulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import securitysimulator.Models.*;
import securitysimulator.Serializers.BuildingSerializer;
import securitysimulator.Thread.ViolationGeneratorThread;
import securitysimulator.Thread.ViolationHandlerThread;

import java.net.URL;
import java.util.ResourceBundle;

public class LabController implements Initializable {
    public Button button_save;
    public Button button_open;
    @FXML
    private ComboBox comboBox_violationType;
    @FXML
    private ComboBox comboBox_floor;
    @FXML
    private ComboBox comboBox_room;
    @FXML
    private ListView  list_datchyky;
    @FXML
    private ListView list_log;
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
        comboBox_violationType.setItems(FXCollections.observableArrayList(ViolationType.values()));


        comboBox_room.setItems(FXCollections.observableArrayList(building.getFloor(0).getRoomList()));
        comboBox_floor.setItems(FXCollections.observableArrayList(building.getFloorsList()));
        comboBox_floor.setValue(building.getFloor(0));
        UpdateRoomsCombo();
        comboBox_violationType.getSelectionModel().selectFirst();
        //comboBox_room.getSelectionModel().selectFirst();
        //comboBox_floor.getSelectionModel().selectFirst();

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
        this.list_datchyky.setItems(FXCollections.observableArrayList(new String[]{"Датчики вогню", "Датчики води", "Датчики газу", "Датчики руху"}));

        createHandlerThread();
    }


    private void UpdateFloorsCombo(){
        comboBox_floor.setItems(FXCollections.observableArrayList(building.getFloorsList()));
        //comboBox_floor.setValue(building.getFloorsList().get(0));
    }

    private void UpdateRoomsCombo(){
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        comboBox_room.setItems(FXCollections.observableArrayList(floor.getRoomList()));
        comboBox_room.setValue(floor.getRoomList().get(0));
    }

    private static ViolationGeneratorThread violationGeneratorThread;
    private static ViolationHandlerThread violationHandlerThread;
    private Building building = new Building();
    private static Object mutex = new Object();
    public void createHandlerThread() {
        violationHandlerThread = new ViolationHandlerThread(building);
        violationHandlerThread.start();
    }

    public void createGeneratorThread() {
        violationGeneratorThread = new ViolationGeneratorThread(building);
        violationGeneratorThread.start();
    }

    public static void killGeneratorThread() {
        if(violationGeneratorThread == null) return;
        violationGeneratorThread.kill();
        violationGeneratorThread = null;
    }
    public static void killHandlerThread() {
        if(violationHandlerThread == null) return;
        violationHandlerThread.kill();
        violationHandlerThread = null;
    }


    public void Save_button_click(ActionEvent actionEvent) {
        System.out.println("Button SAVE");
        BuildingSerializer.Serialize(building);
    }

    public void Open_File_Click(ActionEvent actionEvent) {
        System.out.println("Button OPEN");
        building = BuildingSerializer.Deserialize();

    }

    public void Violation_init_click(ActionEvent actionEvent) {
        ViolationType violation = (ViolationType)comboBox_violationType.getValue();
        Room room = (Room)comboBox_room.getValue();
        if(room == null) return;
        room.addViolation(violation);
    }

    public void Stop_button_click(ActionEvent actionEvent) {
        killGeneratorThread();
    }

    public void Start_button_click(ActionEvent actionEvent) {
        createGeneratorThread();
    }

    public void Remove_Room_click(ActionEvent actionEvent) {
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        floor.removeRoom();
    }

    public void Add_Room_click(ActionEvent actionEvent) {
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        floor.addRoom(2,2,3);
        UpdateRoomsCombo();
    }

    public void Remove_Floor_click(ActionEvent actionEvent) {
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        floor.removeRoom();
        UpdateRoomsCombo();
    }

    public void Add_Floor_btn_click(ActionEvent actionEvent) {
        building.addFloor();
        UpdateFloorsCombo();
    }

    public void ComboBoxFloor_changed(ActionEvent actionEvent) {
        System.out.println("Combo changed");
        UpdateRoomsCombo();
    }
}