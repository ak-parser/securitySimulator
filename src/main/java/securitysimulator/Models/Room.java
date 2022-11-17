package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room implements Serializable {
    private String name;
    private double length;
    private double width;
    private int windows;
    private int doors;
    private final List<ViolationType> violationList;

    public Room(String name, double length, double width, int windows, int doors){
        this.name = name;
        this.setLength(length);
        this.setWidth(width);
        this.setWindows(windows);
        this.setDoors(doors);
        violationList = new ArrayList<>();
    }

    public Room(String name){
        this.name = name;
        this.setLength(2);
        this.setWidth(2);
        this.setWindows(3);
        this.setDoors(1);
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

    public void setWindows(int windows) {
        this.windows = windows;
    }
    public int getWindows() {
        return windows;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }
    public int getDoors() {
        return doors;
    }

    public List<ViolationType> getViolationsList(){
        return violationList;
    }

    public void addViolation(ViolationType newViolation){
        violationList.add(newViolation);
    }

    @Override
    public String toString(){
        return getName();
        //return getLength() + " " + getWidth() + " " + getWindowsAndDoors() + "\n" + violationList.toString() + "\n";
    }
}