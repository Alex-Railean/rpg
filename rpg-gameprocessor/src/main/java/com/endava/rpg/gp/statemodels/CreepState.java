package com.endava.rpg.gp.statemodels;

import org.springframework.stereotype.Component;

@Component
public class CreepState extends State {

    private String creepType;

    public String getCreepType() {
        return creepType;
    }

    public CreepState setCreepType(String creepType) {
        this.creepType = creepType;
        return this;
    }
}
