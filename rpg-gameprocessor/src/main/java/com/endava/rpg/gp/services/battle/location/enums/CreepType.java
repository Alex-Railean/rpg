package com.endava.rpg.gp.services.battle.location.enums;

public enum CreepType {

    HUMANOID("humanoid"), BEAST("beast");

    private String creepType;

    CreepType(String creepType){
        this.creepType = creepType;
    }

    @Override
    public String toString() {
        return creepType;
    }
}
