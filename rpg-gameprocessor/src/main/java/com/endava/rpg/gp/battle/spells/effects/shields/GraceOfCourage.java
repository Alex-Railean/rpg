package com.endava.rpg.gp.battle.spells.effects.shields;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Displayed;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Shield;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

import java.util.Set;

public class GraceOfCourage extends Effect implements Shield, Leveled, Displayed {

    private final int COEFICIENT = 2;

    private Integer points;

    private int level;

    public GraceOfCourage(State holder, EffectCore ec) {
        super(holder, ec);
        this.points = (getHolder().getHp().getValue() / 100 * COEFICIENT) * level;
    }

    @Override
    public void affectTarget() {
        if (points <= 0) {
            setToRemove(true);
        }
    }

    @Override
    public Integer getPoints() {
        return points;
    }

    @Override
    public GraceOfCourage setPoints(int p) {
        this.points = p;
        return this;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public GraceOfCourage setLevel(int level) {
        super.setDescription(super.getRawDescription().replace("$percentage", String.valueOf(COEFICIENT * level)));
        this.level = level;
        return this;
    }

    @Override
    public Effect refreshDuration() {
        this.points = (getHolder().getHp().getValue() / 100 * COEFICIENT) * level;
        return super.refreshDuration();
    }

    @Override
    public boolean addTo(Set<Effect> effects) {
        CombatTextService.createShieldEffectRecord(super.getHolder(), points);
        return super.addTo(effects);
    }
}
