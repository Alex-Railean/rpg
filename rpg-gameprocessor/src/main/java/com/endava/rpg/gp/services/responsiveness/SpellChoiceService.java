package com.endava.rpg.gp.services.responsiveness;

import com.endava.rpg.gp.services.battle.LocationService;
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

    private final LocationService LOCATION;

    private final SpellService SPELL_SERVICE;

    private final CharacterStateService CHAR_STATE_SERVICE;

    @Autowired
    private SpellChoiceService(LocationService locationService, SpellService spellService, CharacterStateService characterStateService) {
        this.LOCATION = locationService;
        this.SPELL_SERVICE = spellService;
        this.CHAR_STATE_SERVICE = characterStateService;
    }

    public void creepResponse() {
        LOCATION.getCreepGroup()
                .forEach(creep -> {
                    if (isAlmostDead(creep)) {
                        List<Spell> spells = LOCATION.getEnemyProtectionSpells(creep);
                        Spell toCast;
                        if (SPELL_SERVICE.isManaEnough(toCast = chooseAppropriate(spells), creep)) {
                            SPELL_SERVICE.useSpellTo(toCast, CHAR_STATE_SERVICE.getCharacterState(), creep);
                        } else if (chooseAnotherOption(LOCATION.getEnemySpells(creep)) != null) {
                            SPELL_SERVICE.useSpellTo(chooseAnotherOption(LOCATION.getEnemySpells(creep)), CHAR_STATE_SERVICE.getCharacterState(), creep);
                        }

                    } else {
                        SPELL_SERVICE.useSpellTo(chooseStrongestSpell(LOCATION.getEnemyAttackSpells(creep)),
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
                .filter(spell -> SPELL_SERVICE.isManaEnough(spell, LOCATION.getCurrentEnemy()))
                .findFirst()
                .orElse(null);
    }

    private Spell chooseAppropriate(List<Spell> spells) {
        Spell strongest = chooseStrongestSpell(spells);

        Spell weakest = spells.stream()
                .min(Comparator.comparing(Spell::getCoefficient))
                .orElse(null);

        return isRisky() ?
                SPELL_SERVICE.isManaEnough(strongest, LOCATION.getCurrentEnemy()) ? strongest : weakest
                : weakest;
    }

    private boolean isRisky() {
        return LOCATION.getCreepGroup().size() <= 4;
    }

    // TODO: Move risk coefficient (1.8)
    private boolean isAlmostDead(CreepState creep) {
        return !creep.getHp().equals(creep.getCurrentHp()) &&
        creep.getCurrentHp() +
                LOCATION.getCurrentEnemy().getShieldPoints() <
                SPELL_SERVICE.getBiggestDmg() * 1.8;
    }
}
