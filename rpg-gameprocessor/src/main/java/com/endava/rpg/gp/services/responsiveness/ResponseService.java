package com.endava.rpg.gp.services.responsiveness;

import com.endava.rpg.gp.services.battle.SpellService;
import com.endava.rpg.gp.services.battle.location.LocationService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ResponseService {

    private final LocationService LOCATION;

    private final SpellService SPELL_SERVICE;

    private final CharacterStateService CHAR_STATE;

    @Autowired
    private ResponseService(LocationService locationService, SpellService spellService, CharacterStateService characterStateService) {
        this.LOCATION = locationService;
        this.SPELL_SERVICE = spellService;
        this.CHAR_STATE = characterStateService;
    }

    public void creepResponse() {
        LOCATION.getCreepGroup()
                .forEach(creep -> {
                    if (isAlmostDead(creep)) {
                        List<Spell> spells = SPELL_SERVICE.getEnemyProtectionSpells(creep);
                        Spell toCast;
                        if (SPELL_SERVICE.isEnoughMana(toCast = chooseAppropriate(spells), creep)) {
                            SPELL_SERVICE.useSpellTo(toCast, CHAR_STATE.getCharacterState(), creep);
                        } else if (chooseAnotherOption(SPELL_SERVICE.getEnemySpells(creep)) != null) {
                            SPELL_SERVICE.useSpellTo(chooseAnotherOption(SPELL_SERVICE.getEnemySpells(creep)), CHAR_STATE.getCharacterState(), creep);
                        }

                    } else {
                        //TODO: OOM option
                        SPELL_SERVICE.useSpellTo(chooseStrongestSpell(SPELL_SERVICE.getEnemyAttackSpells(creep)),
                                CHAR_STATE.getCharacterState(),
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
                .filter(spell -> SPELL_SERVICE.isEnoughMana(spell, LOCATION.getCurrentEnemy()))
                .findFirst()
                .orElse(null);
    }

    private Spell chooseAppropriate(List<Spell> spells) {
        Spell strongest = chooseStrongestSpell(spells);

        Spell weakest = spells.stream()
                .min(Comparator.comparing(Spell::getCoefficient))
                .orElse(null);

        return isRisky() ?
                SPELL_SERVICE.isEnoughMana(strongest, LOCATION.getCurrentEnemy()) ? strongest : weakest
                : weakest;
    }

    private boolean isRisky() {
        return LOCATION.getCreepGroup().size() <= 4;
    }

    // TODO: Move risk coefficient (1.8)
    private boolean isAlmostDead(CreepState creep) {
        return !creep.getHp().getValue().equals(creep.getHp().getCurrentValue()) &&
                creep.getHp().getCurrentValue() +
                        LOCATION.getCurrentEnemy().getShieldPoints() <
                        SPELL_SERVICE.getBiggestDmg() * 1.8;
    }
}