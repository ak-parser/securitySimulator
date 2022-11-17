package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Floor implements Serializable {
    private final List<Room> roomList;

    public Floor(){
        roomList = Collections.synchronizedList(new ArrayList<>());
    }

    public Room getRandomRoom() throws Exception{
        if(roomList.isEmpty()) throw new Exception("No rooms");
        Random randomizer = new Random();
        return roomList.get(randomizer.nextInt(roomList.size()));
    }

    public List<Room> getRoomList(){
        return roomList;
    }

    public void addRoom(double length, double width, int windowsAndDoors){
        roomList.add(new Room(length, width, windowsAndDoors));
    }

    @Override
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        for (Room room : roomList) {
            toReturn.append(room.toString()).append("\n");
        }
        return toReturn.toString();
    }
}
