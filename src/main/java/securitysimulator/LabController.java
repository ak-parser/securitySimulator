package securitysimulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import securitysimulator.Logger.FileLoggerDecorator;
import securitysimulator.Logger.ILogger;
import securitysimulator.Logger.UILoggerDecorator;
import securitysimulator.Models.*;
import securitysimulator.Serializers.BuildingSerializer;
import securitysimulator.Thread.ViolationGeneratorThread;
import securitysimulator.Thread.ViolationHandlerThread;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


    public void Save_button_click(ActionEvent actionEvent) {
        System.out.println("Button SAVE");
        BuildingSerializer.Serialize(building);
    }

    public void Open_File_Click(ActionEvent actionEvent) {
        /*System.out.println("Button OPEN");
        building = BuildingSerializer.Deserialize();*/
        Label label = new Label();
        label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        if(LocalDateTime.now().getSecond()%5>2) {
            label.setStyle("-fx-background-color:#7C987C;");
        }else {
            label.setStyle("-fx-background-color:#984A65;");
        }

        oListLabels.add(0, label);
        if(oListLabels.size() > 5) oListLabels.remove(oListLabels.size()-1);

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
        UpdateRoomsCombo();
    }
}