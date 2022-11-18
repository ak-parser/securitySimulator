package securitysimulator.Thread;

import securitysimulator.Generators.RandomViolationGenerator;
import securitysimulator.Models.Building;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

import java.time.LocalDateTime;
import java.util.Random;

public class ViolationGeneratorThread implements Runnable {
    private final Building building;
    private final Thread thread;
    private boolean exit = false;

    public ViolationGeneratorThread(Building _building) {
        building = _building;
        thread = new Thread(this);
        System.out.println("New thread created: " + thread);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        while (!exit) {
            for (Floor floor : building.getFloorsList()){
                for(Room room : floor.getRoomList()){
                    int d = (int)( (room.getDoors() + room.getWindows()) *
                            room.getLength() *
                            room.getWidth());
                    Random randomizer = new Random();
                    if(d > randomizer.nextInt(4000)){
                        ViolationType violationType = RandomViolationGenerator.GenerateViolation();
                        synchronized (room){
                            room.addViolation(violationType);
                        }
                        System.out.println("GENERATED " + violationType + " " + floor + " " + room);
                    }

                }
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {

            }
        }
    }

    public void kill() {
        exit = true;
    }
}
