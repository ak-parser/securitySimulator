package securitysimulator.Logger;

import securitysimulator.Models.*;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class BaseLogger implements ILogger {
    ILogger wrapee;
    public BaseLogger(){}
    public BaseLogger(ILogger wrapee){
        SetWrapee(wrapee);
    }
    public void SetWrapee(ILogger wrapee){
        this.wrapee = wrapee;
    }
    public void LogViolation(Floor floor, Room room, ViolationType vType, LocalDateTime date){
        if(wrapee != null)
        wrapee.LogViolation(floor, room, vType, date);
    }

    public void LogReaction(Floor floor, Room room, String stringAction, LocalDateTime date){
        if(wrapee != null)
        wrapee.LogReaction(floor, room, stringAction, date);
    }

    public void close() throws IOException {
        if(wrapee != null)
        wrapee.close();
    }

}
