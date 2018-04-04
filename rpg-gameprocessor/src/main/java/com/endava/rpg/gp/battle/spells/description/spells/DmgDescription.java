package com.endava.rpg.gp.battle.spells.description.spells;

import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.utils.DescribedSpell;

import java.util.List;
import java.util.stream.Collectors;

public class DmgDescription implements DescribedSpell {

    private DescribedSpell describedSpell;

    public DmgDescription(DescribedSpell d) {
        this.describedSpell = d;
    }

    public static List<DescribedSpell> toDmgDescription(List<DescribedSpell> spells) {
        return spells.stream().map(DmgDescription::new).collect(Collectors.toList());
    }

    @Override
    public Spell getSpell() {
        return describedSpell.getSpell();
    }

    @Override
    public String getDescription() {
        Integer dmg = FormulaService.getDamage(CharacterStateService.getLvl(), getSpell().getCoefficient());
        String dmgDescription = "DMG -> " + FormulaService.getMinDmg(dmg) + "-" + FormulaService.getMaxDmg(dmg);
        return describedSpell.getDescription() + "\n" + dmgDescription;
    }
}
