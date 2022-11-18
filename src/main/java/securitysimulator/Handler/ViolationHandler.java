package securitysimulator.Handler;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
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

    private void SetLabelStyling(Label label){
        Platform.runLater(()->{
            synchronized (label) {
                label.setStyle("-fx-text-fill:red;");
            }
        });

    }
    private void ResetLabelStyling(Label label){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        Platform.runLater(()->{
            synchronized (label) {
                label.setStyle("");
            }
        });
    }

    public void handle(Room currentRoom, ObservableList<Label> oListLabelsDatchiky) {
        //System.out.println("Handling..");
        for (Floor floor : building.getFloorsList()) {
            for (Room room : floor.getRoomList()) {
                synchronized (room) {
                    boolean violationHandled = false;
                    boolean isThisRoom = currentRoom == room;
                    for (ViolationType violationType : room.getViolationsList()) {
                        violationHandled = true;
                        synchronized (logger) {
                            logger.LogViolation(floor, room, violationType, LocalDateTime.now());
                            switch (violationType){
                                case Fire -> {
                                    new Thread(() -> {
                                        if(isThisRoom)
                                            SetLabelStyling(oListLabelsDatchiky.get(0));
                                        try {
                                            Thread.sleep(700);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Зупущено систему тушіння вогню", LocalDateTime.now());
                                        try {
                                            Thread.sleep(700);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Зупущено звукову сигналізацію", LocalDateTime.now());
                                        try {
                                            Thread.sleep(300);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Людей сповіщено про термінову евакуацію", LocalDateTime.now());
                                        try {
                                            Thread.sleep(1200);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Викличено службу ДСНС (101)", LocalDateTime.now());
                                        if(isThisRoom)
                                            ResetLabelStyling(oListLabelsDatchiky.get(0));
                                    }).start();

                                }
                                case Flood ->{
                                    new Thread(() -> {
                                        if(isThisRoom)
                                            SetLabelStyling(oListLabelsDatchiky.get(1));
                                        try {
                                            Thread.sleep(800);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Викличено службу ДСНС (101)", LocalDateTime.now());
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Людей сповіщено про евакуацію", LocalDateTime.now());
                                        if(isThisRoom)
                                            ResetLabelStyling(oListLabelsDatchiky.get(1));
                                    }).start();
                                }
                                case Gas -> {
                                    new Thread(() -> {
                                        if(isThisRoom)
                                            SetLabelStyling(oListLabelsDatchiky.get(2));
                                        try {
                                            Thread.sleep(800);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Викличено службу ДСНС (101)", LocalDateTime.now());
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Людей сповіщено про евакуацію", LocalDateTime.now());
                                        if(isThisRoom)
                                            ResetLabelStyling(oListLabelsDatchiky.get(2));
                                    }).start();
                                }
                                case Movement -> {
                                    new Thread(() -> {
                                        if(isThisRoom)
                                            SetLabelStyling(oListLabelsDatchiky.get(3));
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Очікування вводу паролю безпеки", LocalDateTime.now());
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Пароль не було введено!", LocalDateTime.now());
                                        try {
                                            Thread.sleep(800);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Викличено поліцію (102)", LocalDateTime.now());
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Включено звукову сигналізацію", LocalDateTime.now());
                                        if(isThisRoom)
                                            ResetLabelStyling(oListLabelsDatchiky.get(3));
                                    }).start();
                                }
                                case  Invasion -> {
                                    new Thread(() -> {
                                        if(isThisRoom)
                                            SetLabelStyling(oListLabelsDatchiky.get(4));
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Викличено поліцію (102)", LocalDateTime.now());
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {}
                                        logger.LogReaction(floor, room, "Включено звукову сигналізацію", LocalDateTime.now());
                                        if(isThisRoom)
                                            ResetLabelStyling(oListLabelsDatchiky.get(4));
                                    }).start();
                                }

                            }
                        }
                    }
                    if (violationHandled) {
                        room.getViolationsList().clear();
                    }
                }
            }
        }
    }
}
