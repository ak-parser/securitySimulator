package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Building implements Serializable {
    private final List<Floor> floorsList;

    public Building(){
        floorsList = Collections.synchronizedList(new ArrayList<>());
    }

    public Floor getRandomFloor() throws Exception {
        if (floorsList.isEmpty()) throw new Exception("No floors");
        Random randomizer = new Random();
        return floorsList.get(randomizer.nextInt(floorsList.size()));
    }

    public void addFloor(){
        floorsList.add(new Floor());
    }

    public List<Floor> getFloorsList(){
        return floorsList;
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