package securitysimulator.Logger;

import securitysimulator.Models.*;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UILoggerDecorator extends BaseLogger{
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");

    public UILoggerDecorator(){
    }
    public UILoggerDecorator(ILogger wrapee){
        super(wrapee);
    }
    private void LogToUI(String msg, LocalDateTime date){
        System.out.println(date.format(dtf) + " | " + msg);
    }

    public void LogViolation(Floor floor, Room room, ViolationType vType, LocalDateTime date){
        String msg = "Detected " + vType + " on " + floor + " in " + room;

        LogToUI("VOILIATION | " + msg, date);

        super.LogViolation(floor, room, vType, date);
    }

    public void LogReaction(Floor floor, Room room, String stringAction, LocalDateTime date){
        String msg = stringAction;

        LogToUI("REACTION   | " + msg, date);

        super.LogReaction(floor, room, stringAction, date);
    }
}
