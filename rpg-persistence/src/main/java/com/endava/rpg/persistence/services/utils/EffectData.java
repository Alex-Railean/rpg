package com.endava.rpg.persistence.services.utils;

import com.endava.rpg.persistence.models.EffectCore;

public class EffectData {
    public static EffectCore modify(EffectCore e) {
        e.modify();
        if (e.getDuration() != -1) {
            e.setDescription(e.getDescription() + "\nDuration: " + (e.getDuration() - 1));
        }
        if (e.getDescription().contains("$")) {
            //TODO: implement dmg description
        }
        return e;
    }
}
