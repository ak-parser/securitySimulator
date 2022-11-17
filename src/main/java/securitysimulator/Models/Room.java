package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room implements Serializable {
    private String name;
    private double length;
    private double width;
    private int windowsAndDoors;
    private final List<ViolationType> violationList;

    public Room(String name, double length, double width, int windowsAndDoors){
        this.name = name;
        this.setLength(length);
        this.setWidth(width);
        this.setWindowsAndDoors(windowsAndDoors);
//        violationList = Collections.synchronizedList(new ArrayList<>());
        violationList = new ArrayList<>();
    }

    public Room(String name){
        this.name = name;
        this.setLength(2);
        this.setWidth(2);
        this.setWindowsAndDoors(3);
//        violationList = Collections.synchronizedList(new ArrayList<>());
        violationList = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getLength() {
        return length;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public double getWidth() {
        return width;
    }

    public void setWindowsAndDoors(int windowsAndDoors) {
        this.windowsAndDoors = windowsAndDoors;
    }
    public int getWindowsAndDoors() {
        return windowsAndDoors;
    }

    public List<ViolationType> getViolationsList(){
        return violationList;
    }

    public void addViolation(ViolationType newViolation){
        violationList.add(newViolation);
    }

    @Override
    public String toString(){
        return name;
        //return getLength() + " " + getWidth() + " " + getWindowsAndDoors() + "\n" + violationList.toString() + "\n";
    }
}