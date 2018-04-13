package com.endava.rpg.gp.battle.spells.description.effects;

import com.endava.rpg.gp.battle.spells.effects.Effect;

public class EffectData {
    public static Effect modify(Effect e) {
        if (e.getCurrentDuration() != -1) {
            e.setDescription(e.getRawDescription() + "\nDuration: $duration");
        }

        if (e.getRawDescription().contains("$")) {
            //TODO: implement dmg description
        }

        return e;
    }

    public static String updateDuration(Effect e) {
        return e.getRawDescription().replace("$duration", String.valueOf(e.getCurrentDuration()));
    }
}
