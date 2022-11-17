package securitysimulator.Models;

import java.io.Serializable;

public enum ViolationType implements Serializable {
    Fire,
    Flood,
    Gas,
    Invasion,
    Movement;

    @Override
    public String toString(){
        return this.name();
    }
}
