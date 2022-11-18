package securitysimulator.Thread;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import securitysimulator.Handler.ViolationHandler;
import securitysimulator.Logger.FileLoggerDecorator;
import securitysimulator.Logger.ILogger;
import securitysimulator.Logger.UILoggerDecorator;
import securitysimulator.Models.Building;
import securitysimulator.Models.Room;

public class ViolationHandlerThread implements Runnable {
    private final Thread thread;
    ViolationHandler violationHandler;
    private boolean exit = false;
    private ObservableList<Label> oListLabelsDatchiky;

    private static Room currentRoom;

    public ViolationHandlerThread(Building building, ILogger logger, ObservableList<Label> oListLabelsDatch) {
        currentRoom = building.getFloor(0).getRoom(0);
        oListLabelsDatchiky = oListLabelsDatch;
        violationHandler = new ViolationHandler(building, logger);
        thread = new Thread(this);
        System.out.println("New thread: " + thread);
    }

    public static void SetRoom(Room room){
        if(currentRoom == null) return;
        synchronized (currentRoom){
            currentRoom = room;
        }
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
            violationHandler.handle(currentRoom, oListLabelsDatchiky);
        }
    }
    public void kill() {
        exit = true;
    }
}
