package com.endava.rpg.gp.services.responsiveness;

import com.endava.rpg.gp.services.battle.CreepLocationService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SpellChoiceService {

    private final CreepLocationService CREEP_LOCATION;

    private final SpellService SPELL_SERVICE;

    private final CharacterStateService CHAR_STATE_SERVICE;

    @Autowired
    public SpellChoiceService(CreepLocationService creepLocationService, SpellService spellService, CharacterStateService characterStateService) {
        this.CREEP_LOCATION = creepLocationService;
        this.SPELL_SERVICE = spellService;
        this.CHAR_STATE_SERVICE = characterStateService;
    }

    public void creepResponse() {
        CREEP_LOCATION.getCreepGroup()
                .forEach(creep -> {
                    if (isAlmostDead(creep)) {
                        List<Spell> spells = CREEP_LOCATION.getEnemyProtectionSpells(creep);

                        if (SPELL_SERVICE.isManaEnough(chooseAppropriate(spells), creep)) {
                            SPELL_SERVICE.useSpellTo(chooseAppropriate(spells), CHAR_STATE_SERVICE.getCharacterState(), creep);
                        } else if (chooseAnotherOption(CREEP_LOCATION.getEnemySpells(creep)) != null) {
                            SPELL_SERVICE.useSpellTo(chooseAnotherOption(CREEP_LOCATION.getEnemySpells(creep)), CHAR_STATE_SERVICE.getCharacterState(), creep);
                        }

                    } else {
                        SPELL_SERVICE.useSpellTo(chooseStrongestSpell(CREEP_LOCATION.getEnemyAttackSpells(creep)),
                                CHAR_STATE_SERVICE.getCharacterState(),
                                creep);
                    }
                });
    }

    private Spell chooseStrongestSpell(List<Spell> spells) {
        return spells.stream()
                .max(Comparator.comparing(Spell::getCoefficient))
                .orElse(null);
    }

    private Spell chooseAnotherOption(List<Spell> spells) {
        return spells.stream()
                .filter(spell -> SPELL_SERVICE.isManaEnough(spell, CREEP_LOCATION.getCurrentEnemy()))
                .findFirst()
                .orElse(null);
    }

    private Spell chooseAppropriate(List<Spell> spells) {
        Spell strongest = chooseStrongestSpell(spells);

        Spell weakest = spells.stream()
                .min(Comparator.comparing(Spell::getCoefficient))
                .orElse(null);

        return isRisky() ?
                SPELL_SERVICE.isManaEnough(strongest, CREEP_LOCATION.getCurrentEnemy()) ? strongest : weakest
                : weakest;
    }

    private boolean isRisky() {
        return CREEP_LOCATION.getCreepGroup().size() <= 4;
    }

    private boolean isAlmostDead(CreepState creep) {
        return creep.getCurrentHp() +
                CREEP_LOCATION.getCurrentEnemy().getShieldPoints() <
                SPELL_SERVICE.getBiggestDmg() * 2;
    }
}
