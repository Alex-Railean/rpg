package com.endava.rpg.gp.statemodels;

import org.springframework.stereotype.Component;

@Component
public class CreepState extends State {

    private String creepName;

    private Integer creepLevel;

    private String creepType;

    public String getCreepName() {
        return creepName;
    }

    public CreepState setCreepName(String creepName) {
        this.creepName = creepName;
        return this;
    }

    public Integer getCreepLevel() {
        return creepLevel;
    }

    public CreepState setCreepLevel(Integer creepLevel) {
        this.creepLevel = creepLevel;
        return this;
    }

    public String getCreepType() {
        return creepType;
    }

    public CreepState setCreepType(String creepType) {
        this.creepType = creepType;
        return this;
    }
}
