package securitysimulator.Logger;

import securitysimulator.Models.*;
import securitysimulator.Models.Floor;
import securitysimulator.Models.Room;
import securitysimulator.Models.ViolationType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLoggerDecorator extends BaseLogger {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
    private FileWriter logFileWriter;
    public FileLoggerDecorator(String filename){
        SetFile(filename);
    }
    public FileLoggerDecorator(ILogger wrapee, String filename){
        super(wrapee);
        SetFile(filename);
    }
    public void SetFile(String filename){
        try {
            File logFile = new File(filename);
            if(logFile.createNewFile()){
                System.out.println("<+> Log file created");
            }
            logFileWriter = new FileWriter(filename, true);
            System.out.println("<+> Log file opened");

            LogToFile("\n" + LocalDateTime.now().format(dtf) + "     START SESSION");

        } catch (IOException e) {
            System.out.println("<!> Log creation/opening fail");
        }
    }

    public void close() throws IOException {
        logFileWriter.close();
        System.out.println("<+> Log file closed");
        super.close();
    }

    private void LogToFile(String msg) {
        try {
            logFileWriter.write(msg + "\n");
        } catch (IOException e) {
            System.out.println("<!> Message was not logged to file");
        }
    }

    public void LogViolation(Floor floor, Room room, ViolationType vType, LocalDateTime date){
        String msg = "Detected " + vType + " on " + floor + " in " + room;
        msg = date.format(dtf) + " [!] " + msg;

        LogToFile(msg);

        super.LogViolation(floor, room, vType, date);
    }

    public void LogReaction(Floor floor, Room room, String stringAction, LocalDateTime date){
        String msg = stringAction;
        msg = date.format(dtf) + " [!] " + msg;

        LogToFile(msg);

        super.LogReaction(floor, room, stringAction, date);
    }
}
