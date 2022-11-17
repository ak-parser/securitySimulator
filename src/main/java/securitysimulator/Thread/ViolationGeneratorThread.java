package securitysimulator.Thread;

import securitysimulator.Generators.RandomViolationGenerator;
import securitysimulator.Models.Building;
import securitysimulator.Models.ViolationType;

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

            ViolationType violationType = RandomViolationGenerator.GenerateViolation();
            try {
                building.getRandomFloor().getRandomRoom().addViolation(violationType);
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
