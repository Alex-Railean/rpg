package com.endava.rpg.gp.battle.spells.effects.shields;

import com.endava.rpg.gp.battle.ExpService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.battle.spells.constants.AttributeType;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Displayed;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Experienced;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Shield;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

import java.util.Set;

// TODO: Bug -> Deals no dmg
public class UnstableShield extends Effect implements Shield, Displayed, Experienced {

    private Integer points;

    private Integer totalDmg = 0;

    private Integer explosionDamage = 0;

    public UnstableShield(State target, Spell s) {
        super(target, s.getEffectCore());
        this.points = FormulaService.getShield(target, s.getCoefficient());
    }

    @Override
    public Effect remove() {
        setPoints(0);
        affectTarget();

        return super.remove();
    }

    @Override
    public void affectTarget() {
        if (points <= 0) {
            EnemyService.getCreepGroup().forEach(c -> SpellService.dealDamageTo(c, totalDmg += explosionDamage));
            setToRemove(true);
            expPolicy();
            CombatTextService.createCustomMessage("(Effect)",
                    getName() + " damaged all enemy group. Damage: -" + explosionDamage);
        }
    }

    @Override
    public Integer getPoints() {
        return points;
    }

    @Override
    public UnstableShield setPoints(int p) {
        explosionDamage = points - p;
        this.points = p;
        return this;
    }

    @Override
    public boolean addTo(Set<Effect> effects) {
        CombatTextService.createShieldEffectRecord(super.getHolder(), points);
        return super.addTo(effects);
    }

    @Override
    public void expPolicy() {
        ExpService.addAttributeExp(AttributeType.STRENGTH, totalDmg);
        totalDmg = 0;
    }
}
