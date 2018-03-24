package com.endava.rpg.persistence.services.utils;

import com.endava.rpg.persistence.models.Spell;

public interface DescribedSpell {
    Spell getSpell();

    String getDescription();
}
