package securitysimulator.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room implements Serializable {
    private double length;
    private double width;
    private int windowsAndDoors;
    private final List<ViolationType> violationList;

    public Room(double length, double width, int windowsAndDoors){
        this.setLength(length);
        this.setWidth(width);
        this.setWindowsAndDoors(windowsAndDoors);
        violationList = Collections.synchronizedList(new ArrayList<>());
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
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
        return getLength() + " " + getWidth() + " " + getWindowsAndDoors() + "\n" + violationList.toString() + "\n";
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setWindowsAndDoors(int windowsAndDoors) {
        this.windowsAndDoors = windowsAndDoors;
    }
}