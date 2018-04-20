package com.endava.rpg.gp.battle.spells.description;

import com.endava.rpg.gp.battle.spells.description.spells.DmgDescription;
import com.endava.rpg.gp.battle.spells.description.spells.EffectDescription;
import com.endava.rpg.persistence.services.utils.DescribedSpell;

import java.util.List;

public class DescriptionService {

    public static DescribedSpell addFull(DescribedSpell ds) {
        return new EffectDescription(new DmgDescription(ds));
    }

    public static List<DescribedSpell> addFull(List<DescribedSpell> spells) {
        return EffectDescription.toEffectDescription(DmgDescription.toDmgDescription(spells));
    }
}
