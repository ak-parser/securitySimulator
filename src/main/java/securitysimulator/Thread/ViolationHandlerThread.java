package securitysimulator.Thread;

import securitysimulator.Handler.ViolationHandler;
import securitysimulator.Models.Building;

public class ViolationHandlerThread implements Runnable {
    private final Building building;
    private final Thread thread;
    private boolean exit = false;

    public ViolationHandlerThread(Building _building) {
        building = _building;
        thread = new Thread(this);
        System.out.println("New thread: " + thread);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        ViolationHandler violationHandler = new ViolationHandler(building);
        while (!exit) {
            violationHandler.handle();
        }
    }

    public void kill() {
        exit = true;
    }
}
