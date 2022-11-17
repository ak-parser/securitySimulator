package securitysimulator.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class TestLogger {
    public static void Log(String msg, ObservableList<Label> list){
        Label label = new Label(msg);
        list.add(0, label);
    }
}
