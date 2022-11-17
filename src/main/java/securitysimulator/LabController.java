package securitysimulator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import securitysimulator.Logger.FileLoggerDecorator;
import securitysimulator.Logger.ILogger;
import securitysimulator.Logger.TestLogger;
import securitysimulator.Logger.UILoggerDecorator;
import securitysimulator.Models.*;
import securitysimulator.Serializers.BuildingSerializer;
import securitysimulator.Thread.ViolationGeneratorThread;
import securitysimulator.Thread.ViolationHandlerThread;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class LabController implements Initializable {
    public Button button_save;
    public Button button_open;
    @FXML private ComboBox comboBox_violationType;
    @FXML private ComboBox comboBox_floor;
    @FXML private ComboBox comboBox_room;
    @FXML private ListView  list_datchyky;
    @FXML private ListView list_log;
    @FXML private Spinner spinner_area1;
    @FXML private Spinner spinner_area2;
    @FXML private Spinner spinner_doors;
    @FXML private Spinner spinner_windows;

    @FXML private Button button_start_generator;
    @FXML private Button button_stop_generator;
    @FXML private Button button_start_handler;
    @FXML private Button button_stop_handler;

    @FXML private Label label_floors_count;
    @FXML private Label label_rooms_count;

    private ILogger logger;
    private ObservableList<Label> oListLabels;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        oListLabels = FXCollections.observableArrayList(new ArrayList<Label>());
        logger = new FileLoggerDecorator("log.txt");
        logger = new UILoggerDecorator(logger, oListLabels);
        list_log.setItems(oListLabels);

        button_stop_generator.setVisible(false);
        button_start_handler.setVisible(false);

        comboBox_violationType.setItems(FXCollections.observableArrayList(ViolationType.values()));

        comboBox_room.setItems(FXCollections.observableArrayList(building.getFloor(0).getRoomList()));
        comboBox_floor.setItems(FXCollections.observableArrayList(building.getFloorsList()));
        comboBox_floor.setValue(building.getFloor(0));
        comboBox_violationType.getSelectionModel().selectFirst();
        //comboBox_room.getSelectionModel().selectFirst();
        //comboBox_floor.getSelectionModel().selectFirst();

        spinner_windows.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100)
        );
        spinner_doors.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100)
        );
        spinner_area1.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100)
        );
        spinner_area2.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100)
        );

        {
            var labels = new ArrayList<Label>();
            labels.add(new Label("Датчики вогню"));
            labels.add(new Label("Датчики води"));
            labels.add(new Label("Датчики газу"));
            labels.add(new Label("Датчики руху"));
            labels.add(new Label("Датчики проникнення"));

            list_datchyky.setItems(FXCollections.observableArrayList(
                    labels
            ));
        }

        UpdateRoomsCombo();
        UpdateFloorsCountLabel();
        UpdateRoomsCountLabel();

        createHandlerThread();

    }

    private static ViolationGeneratorThread violationGeneratorThread;
    private static ViolationHandlerThread violationHandlerThread;
    private Building building = new Building();
    public void createHandlerThread() {
        violationHandlerThread = new ViolationHandlerThread(building, logger);
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

    private void UpdateFloorsCountLabel(){
        label_floors_count.setText("Поверхи " + building.getFloorsList().size());
    }
    private void UpdateRoomsCountLabel(){
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        label_rooms_count.setText("Кімнати " + floor.getRoomList().size());
    }

    private void UpdateFloorsCombo(){
        comboBox_floor.setItems(FXCollections.observableArrayList(building.getFloorsList()));
    }

    private void UpdateRoomsCombo(){
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        comboBox_room.setItems(FXCollections.observableArrayList(floor.getRoomList()));
        comboBox_room.setValue(floor.getRoomList().get(0));
        UpdateSpinboxes();
    }

    public void UpdateSpinboxes(){
        Room room = (Room)comboBox_room.getValue();
        if(room == null) return;
        if(spinner_area1.getValueFactory() == null) return;
        if(spinner_area2.getValueFactory() == null) return;
        if(spinner_doors.getValueFactory() == null) return;
        if(spinner_windows.getValueFactory() == null) return;

        spinner_area1.getValueFactory().setValue(Double.valueOf(room.getLength()));
        spinner_area2.getValueFactory().setValue(Double.valueOf(room.getWidth()));
        spinner_doors.getValueFactory().setValue(Integer.valueOf(room.getDoors()));
        spinner_windows.getValueFactory().setValue(Integer.valueOf(room.getWindows()));
    }

    public void Set_Room_button_Click(ActionEvent actionEvent){
        Room room = (Room)comboBox_room.getValue();
        if(room == null) return;
        room.setLength((double)spinner_area1.getValue());
        room.setWidth((double)spinner_area2.getValue());
        room.setDoors((int)spinner_doors.getValue());
        room.setWindows((int)spinner_windows.getValue());
    }

    public void ComboBoxFloor_changed(ActionEvent actionEvent) {
        UpdateRoomsCountLabel();
        UpdateRoomsCombo();
    }
    public void ComboBoxRoom_changed(ActionEvent actionEvent) {
        UpdateSpinboxes();
    }
    public void Save_button_click(ActionEvent actionEvent) {
        System.out.println("Button SAVE");
        BuildingSerializer.Serialize(building);
    }
    public void Open_File_Click(ActionEvent actionEvent) {
        System.out.println("Button OPEN");
        Building b = BuildingSerializer.Deserialize();
        if(b == null) return;
        building = b;
        UpdateFloorsCombo();
        UpdateRoomsCombo();

    }

    public void Violation_init_click(ActionEvent actionEvent) {
        ViolationType violation = (ViolationType)comboBox_violationType.getValue();
        Room room = (Room)comboBox_room.getValue();
        if(room == null) return;
        room.addViolation(violation);
    }

    public void Stop_generator_button_click(ActionEvent actionEvent) {
        button_stop_generator.setVisible(false);
        button_start_generator.setVisible(true);

        killGeneratorThread();
    }

    public void Start_generator_button_click(ActionEvent actionEvent) {
        button_stop_generator.setVisible(true);
        button_start_generator.setVisible(false);

        createGeneratorThread();
    }

    public void Stop_handler_button_click(ActionEvent actionEvent) {
        button_stop_handler.setVisible(false);
        button_start_handler.setVisible(true);

        killHandlerThread();
    }

    public void Start_handler_button_click(ActionEvent actionEvent) {
        button_stop_handler.setVisible(true);
        button_start_handler.setVisible(false);

        createHandlerThread();
    }

    public void Remove_Room_click(ActionEvent actionEvent) {
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        floor.removeRoom();
        UpdateRoomsCombo();
        UpdateRoomsCountLabel();
    }

    public void Add_Room_click(ActionEvent actionEvent) {
        Floor floor = (Floor) comboBox_floor.getValue();
        if(floor == null) return;
        floor.addRoom();
        UpdateRoomsCombo();
        UpdateRoomsCountLabel();
    }

    public void Remove_Floor_click(ActionEvent actionEvent) {
        building.removeFloor();
        UpdateFloorsCombo();
        UpdateRoomsCombo();
        UpdateFloorsCountLabel();
    }

    public void Add_Floor_btn_click(ActionEvent actionEvent) {
        building.addFloor();
        UpdateFloorsCombo();
        UpdateRoomsCombo();
        UpdateFloorsCountLabel();
    }

}