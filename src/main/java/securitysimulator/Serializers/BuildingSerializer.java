package securitysimulator.Serializers;

import securitysimulator.Models.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class BuildingSerializer {
    private static String _filename = "D:\\Documents\\SER_TEST.txt";

    public static void SetFilename(String filename){
        BuildingSerializer._filename = filename;
        try {
            new File(_filename).createNewFile();
        } catch (IOException e) {
            return;
        }
    }
    public static void Serialize(Building building){
        File file = new File(_filename);
        Gson json = new Gson();
        String str = json.toJson(building);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write(str);

            fileWriter.close();

        } catch (IOException e) {
            return;
        }
    }

    public static Building Deserialize(){
        File file = new File(_filename);
        if(!file.exists()) return null;
        String content;
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception e){
            return null;
        }
        Gson json = new Gson();
        Building building = json.fromJson(content, Building.class);
        return building;
    }
}
