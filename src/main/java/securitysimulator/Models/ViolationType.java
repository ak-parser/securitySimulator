package securitysimulator.Models;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public enum ViolationType implements Serializable {
    Fire,
    Flood,
    Gas,
    Invasion,
    Movement;

    @Override
    public String toString(){
        switch (this){
            case Fire -> {
                return "Пожежа";
            }
            case Flood -> {
                return "Затоплення";
            }
            case Gas -> {
                return "Газ";
            }
            case  Invasion -> {
                return "Вторгнення";
            }
            case Movement -> {
                return "Рух";
            }
            default -> {
                return "";
            }
        }
    }
}
