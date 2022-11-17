package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Building implements Serializable {
    private final List<Floor> floorsList;

//    public Building(){
//        floorsList = Collections.synchronizedList(new ArrayList<>());
//    }
    public Building(){
        floorsList = new ArrayList<>();
        floorsList.add(new Floor("Поверх 1"));
    }

    public Floor getRandomFloor() {
        if (floorsList.isEmpty()) return null;
        Random randomizer = new Random();
        return floorsList.get(randomizer.nextInt(floorsList.size()));
    }

    public void addFloor(){
        int size = floorsList.size();
        floorsList.add(new Floor("Поверх " + (size+1)));
    }
    public void removeFloor(){
        if(floorsList.size() < 2) return;
        floorsList.remove(floorsList.size()-1);
    }

    public List<Floor> getFloorsList(){
        return floorsList;
    }
    public Floor getFloor(int index){
        if(index < 0 || index >= floorsList.size()) return null;
        return floorsList.get(index);
    }

    @Override
    public String toString(){
        StringBuilder toReturn = new StringBuilder();
        for (Floor floor : floorsList) {
            toReturn.append(floor.toString()).append("\n");
        }
        return toReturn.toString();
    }
}