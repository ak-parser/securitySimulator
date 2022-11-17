package securitysimulator.Thread;

import securitysimulator.Generators.RandomViolationGenerator;
import securitysimulator.Models.Building;
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
        System.out.println("Generator started");
        while (!exit) {
            Random randomizer = new Random();
            if(randomizer.nextInt(5) == 1){
                try {
                    Thread.sleep(randomizer.nextInt(10) * 1000);
                } catch (InterruptedException e) {

                }
            }
            ViolationType violationType = RandomViolationGenerator.GenerateViolation();
            System.out.println("Generating " + violationType);
            try {
                Room room = building.getRandomFloor().getRandomRoom();
                synchronized (room){
                    room.addViolation(violationType);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void kill() {
        exit = true;
        System.out.println(thread + " has been killed");
    }
}
