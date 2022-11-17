package securitysimulator.Serializers;

import securitysimulator.Models.*;
import com.google.gson.Gson;

import java.io.*;
import java.io.IOException;

public class BuildingSerializer {
    private static String _filename = "building.dat";

    public static void SetFilename(String filename){
        BuildingSerializer._filename = filename;
        try {
            new File(_filename).createNewFile();
        } catch (IOException e) {
            return;
        }
    }
    public static void Serialize(Building building) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(_filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(building);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static Building Deserialize() {
        try {
            FileInputStream fileInputStream = new FileInputStream(_filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            return (Building) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }
}
