package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Floor implements Serializable {
    private String name;
    private final List<Room> roomList;

    public Floor(String name){
        this.name = name;
        roomList = new ArrayList<>();
        roomList.add(new Room("Кімната 1"));
    }

    public Room getRandomRoom() {
        if(roomList.isEmpty()) return null;
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

    public void addRoom(){
        int size = roomList.size();
        roomList.add(new Room("Кімната " + (size+1)));
    }
    public void removeRoom(){
        if(roomList.size() < 2) return;
        roomList.remove(roomList.size()-1);
    }

    @Override
    public String toString(){
        return getName();
    }
}
