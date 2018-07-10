package com.endava.rpg.gp.talents.talents;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.factories.EffectFactory;
import com.endava.rpg.gp.battle.spells.effects.factories.PassiveFactory;
import com.endava.rpg.gp.battle.spells.effects.passive.Passive;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.EffectCore;
import com.endava.rpg.persistence.services.PersistenceService;

public abstract class PassiveTalent extends Talent {

    private final PersistenceService PS;

    protected PassiveTalent(Branch branch, String name, String link, String URL, PersistenceService ps) {
        super(branch, name, link, URL);
        this.PS = ps;
    }

    protected void addPassive(String effectName) {
        State c = CharacterStateService.getCharacter();

        EffectCore ec = PS.getEffect(effectName);

        Effect effect = EffectFactory.createEffect(c, ec);
        Passive p = PassiveFactory.createPassive(c, effect);

        if (c.containsPassive(p)) {
            c.removePassive(p);
            p.setLevel(super.getPoints());
            c.addPassive(p);
        } else {
            p.setLevel(super.getPoints());
            c.addPassive(p);
        }
    }

    protected void addPassive(Passive p) {
        State c = CharacterStateService.getCharacter();
        if (c.containsPassive(p)) {
            c.removePassive(p);
            c.addPassive(p);
            p.setLevel(super.getPoints());
        } else {
            c.addPassive(p);
            p.setLevel(super.getPoints());
        }
    }
}
