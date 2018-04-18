package com.endava.rpg.gp.talents.talents.aspects;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.EffectFactory;
import com.endava.rpg.gp.battle.spells.effects.subtypes.InnerEffected;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.talents.branches.strength.AspectsBranch;
import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.constants.EffectName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.CURSED_BLADE;

@Component
public class CursedBladeTalent extends Talent {

    private final PersistenceService PS;

    @Autowired
    private CursedBladeTalent(AspectsBranch aspects, PersistenceService ps) {
        aspects.addTalent(this);
        setName(CURSED_BLADE.NAME);
        setLinkName(CURSED_BLADE.LINK);
        setURL("/resources/img/cursed-blade.jpg");
        setDescription("Cursed blade");
        this.PS = ps;
    }

    @Override
    public void affect() {
        if (getPoints() > 0) {
            CharacterState c = CharacterStateService.getCharacter();
            EffectFactory ef = new EffectFactory();

            Effect effect = ef.createEffect(c,
                    EffectName.CURSED_BLADE,
                    PS.getEffect(EffectName.CURSED_BLADE));

            Effect innerEffect = ef.createEffect(new CreepState(),
                    EffectName.CURSED,
                    PS.getEffect(EffectName.CURSED));

            ((Leveled) effect).setLevel(getPoints());
            ((InnerEffected) effect).setInnerEffect(innerEffect);

            c.addEffect(effect);
        }
    }

    @Override
    public void define(Character character) {
        setPoints(character.getAspects().getCursedBlade());
        setLimit(character.getAspects().getCursedBladeLimit());
    }
}
