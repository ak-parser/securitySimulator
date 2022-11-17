package securitysimulator.Models;

import java.util.Random;

public class  RandomViolationGenerator {
    public static ViolationType GenerateViolation(){
        ViolationType[] violationTypes = ViolationType.values();
        Random randomizer = new Random();
        return violationTypes[randomizer.nextInt(violationTypes.length)];
    }
}
