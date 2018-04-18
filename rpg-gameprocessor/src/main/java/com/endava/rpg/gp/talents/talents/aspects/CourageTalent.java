package com.endava.rpg.gp.talents.talents.aspects;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.EffectFactory;
import com.endava.rpg.gp.battle.spells.effects.subtypes.InnerEffected;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.talents.branches.strength.AspectsBranch;
import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.constants.EffectName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.COURAGE;

@Component
public class CourageTalent extends Talent {

    private final PersistenceService PS;

    @Autowired
    private CourageTalent(AspectsBranch aspects, PersistenceService ps) {
        aspects.addTalent(this);
        setName(COURAGE.NAME);
        setLinkName(COURAGE.LINK);
        setURL("/resources/img/courage.jpg");
        setDescription("COURAGE");
        this.PS = ps;
    }

    @Override
    public void affect() {
        if (getPoints() > 0) {
            CharacterState c = CharacterStateService.getCharacter();
            EffectFactory ef = new EffectFactory();

            Effect effect = ef.createEffect(c,
                    EffectName.COURAGE,
                    PS.getEffect(EffectName.COURAGE));

            Effect innerEffect = ef.createEffect(c,
                    EffectName.SHIELD_OF_COURAGE,
                    PS.getEffect(EffectName.SHIELD_OF_COURAGE));

            ((Leveled) effect).setLevel(getPoints());
            ((InnerEffected) effect).setInnerEffect(innerEffect);

            c.addEffect(effect);
        }
    }

    @Override
    public void define(Character character) {
        setPoints(character.getAspects().getCourage());
        setLimit(character.getAspects().getCourageLimit());
    }
}
