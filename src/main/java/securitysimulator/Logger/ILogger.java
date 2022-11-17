package securitysimulator.Logger;

import java.io.Closeable;
import java.time.LocalDateTime;
import securitysimulator.Models.*;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

public interface ILogger extends Closeable {
    void LogViolation(Floor floor, Room room, ViolationType vType, LocalDateTime date);
    void LogReaction(Floor floor, Room room,  String stringAction, LocalDateTime date);
}
