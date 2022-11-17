package securitysimulator.Thread;

import securitysimulator.Handler.ViolationHandler;
import securitysimulator.Logger.FileLoggerDecorator;
import securitysimulator.Logger.ILogger;
import securitysimulator.Logger.UILoggerDecorator;
import securitysimulator.Models.Building;

public class ViolationHandlerThread implements Runnable {
    private final Thread thread;
    ViolationHandler violationHandler;
    private boolean exit = false;

    public ViolationHandlerThread(Building building, ILogger logger) {
        violationHandler = new ViolationHandler(building, logger);
        thread = new Thread(this);
        System.out.println("New thread: " + thread);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            violationHandler.handle();
        }
    }
    public void kill() {
        exit = true;
    }
}
