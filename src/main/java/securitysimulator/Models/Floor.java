package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Floor implements Serializable {
    private String name;
    private final List<Room> roomList;

//    public Floor(){
//        roomList = Collections.synchronizedList(new ArrayList<>());
//    }
    public Floor(String name){
        this.name = name;
        roomList = new ArrayList<>();
        roomList.add(new Room("Room 1"));
    }

    public Room getRandomRoom() throws Exception{
        if(roomList.isEmpty()) throw new Exception("No rooms");
        Random randomizer = new Random();
        return roomList.get(randomizer.nextInt(roomList.size()));
    }
    public String getName(){
        return this.name;
    }
    public List<Room> getRoomList(){
        return roomList;
    }
    public Room getRoom(int index){
        if(index < 0 || index >= roomList.size()) return null;
        return roomList.get(index);
    }

    public void addRoom(double length, double width, int windowsAndDoors){
        int size = roomList.size();
        roomList.add(new Room("Room " + (size+1),length, width, windowsAndDoors));
    }
    public void removeRoom(){
        roomList.remove(roomList.size()-1);
    }

    @Override
    public String toString(){
        return name;
        /*StringBuilder toReturn = new StringBuilder();
        for (Room room : roomList) {
            toReturn.append(room.toString()).append("\n");
        }
        return toReturn.toString();*/
    }
}
