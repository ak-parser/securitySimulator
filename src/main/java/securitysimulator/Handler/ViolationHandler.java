package securitysimulator.Handler;

import securitysimulator.Models.Building;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

public class ViolationHandler {
    private final Building building;

    public ViolationHandler(Building _building) {
        building = _building;
    }

    public void handle() {
        for (Floor floor : building.getFloorsList()) {
            for (Room room : floor.getRoomList()) {
                for (ViolationType violationType : room.getViolationsList()) {
                    System.out.println("Founded violation: " + violationType);
                }
            }
        }
    }
}
