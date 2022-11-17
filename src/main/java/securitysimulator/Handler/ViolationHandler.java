package securitysimulator.Handler;

import securitysimulator.Logger.ILogger;
import securitysimulator.Models.Building;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

import java.time.LocalDateTime;

public class ViolationHandler {
    private final Building building;
    private final ILogger logger;
    private Object loggerMutex;

    public ViolationHandler(Building building, ILogger logger) {
        this.building = building;
        this.logger = logger;
    }

    public void handle() {
        System.out.println("Handling..");
        for (Floor floor : building.getFloorsList()) {
            for (Room room : floor.getRoomList()) {
                synchronized (room) {
                    boolean violationHandled = false;
                    for (ViolationType violationType : room.getViolationsList()) {
                        violationHandled = true;
                        
                        synchronized (logger) {
                            logger.LogViolation(floor, room, violationType, LocalDateTime.now());
                        }
                    /*switch (violationType){
                        case Fire -> {

                        }
                        case Flood -> {

                        }
                        case Gas -> {

                        }
                        case  Invasion -> {

                        }
                        case Movement -> {

                        }
                        default -> {

                        }
                    }*/
                    }
                    if (violationHandled) {
                        room.getViolationsList().clear();
                    }
                }
            }
        }
    }
}
