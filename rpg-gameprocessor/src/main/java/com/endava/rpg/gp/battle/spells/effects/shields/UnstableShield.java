package com.endava.rpg.gp.battle.spells.effects.shields;

import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class UnstableShield extends Effect implements Shield {

    private int points;

    private int explosionDamage = 0;

    public UnstableShield(State target, Spell s) {
        super(target, s.getEffectCore());
        this.points = FormulaService.getShield(target, s.getCoefficient());
    }

    @Override
    public boolean remove(Effect e) {
        Shield s = (Shield) e;
        s.setPoints(0);
        e.affectTarget();

        return getHolder().getEffects().remove(e);
    }

    @Override
    public void affectTarget() {
        if (points <= 0) {
            EnemyService.getCreepGroup().forEach(c -> SpellService.damageIt(c, explosionDamage));
            getHolder().getEffects().remove(this);
            CombatTextService.createCustomMessage("(Effect)",
                    getName() + " damaged all enemy group. Damage: -" + explosionDamage);
        }
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public Shield setPoints(int p) {
        explosionDamage = points - p;
        this.points = p;
        return this;
    }
}
