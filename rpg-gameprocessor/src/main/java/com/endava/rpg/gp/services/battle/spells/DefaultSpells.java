package com.endava.rpg.gp.services.battle.spells;

public enum DefaultSpells {

    SWORD_ATTACK("Sword Attack"), BOW_ATTACK("Bow Attack"), FIRE_BALL("Fire Ball");

    private String spellName;

    DefaultSpells(String spellName) {
        this.spellName = spellName;
    }

    @Override
    public String toString() {
        return spellName;
    }
}
