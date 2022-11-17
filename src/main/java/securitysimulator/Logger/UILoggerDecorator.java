package securitysimulator.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import securitysimulator.Models.*;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UILoggerDecorator extends BaseLogger{
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
    private String violationMessageStyle = "-fx-text-fill:#DA4B23";
    private String reactionMessageStyle = "-fx-text-fill:#2170BF";
    private ObservableList<Label> oListLabels;
    private int recordsCountLimit = 10;
    public UILoggerDecorator(ObservableList<Label> observableLabelList){
        this.oListLabels = observableLabelList;
    }
    public UILoggerDecorator(ILogger wrapee, ObservableList<Label> observableLabelList){
        super(wrapee);
        this.oListLabels = observableLabelList;
    }
    private void setLimit(int limit){
        if(limit < 2) return;
        this.recordsCountLimit = limit;
    }
    private void LogToUI(String msg, String style, LocalDateTime date){
        Label label = new Label();
        label.setText(date.format(dtf) + " | " + msg);
        label.setStyle(style);

        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  oListLabels.add(0, label);
                                  if(oListLabels.size() > recordsCountLimit){
                                      oListLabels.remove(oListLabels.size()-1);
                                  }
                              }
                          });


    }

    public void LogViolation(Floor floor, Room room, ViolationType vType, LocalDateTime date){
        String msg = "Detected " + vType + " on " + floor + " in " + room;

        LogToUI(msg, violationMessageStyle, date);

        super.LogViolation(floor, room, vType, date);
    }

    public void LogReaction(Floor floor, Room room, String stringAction, LocalDateTime date){
        String msg = stringAction;

        LogToUI(msg, reactionMessageStyle, date);

        super.LogReaction(floor, room, stringAction, date);
    }
}
