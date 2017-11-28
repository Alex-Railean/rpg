package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SpellService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellService.class);

    @Autowired
    private ActionBarService actionBarService;

    @Autowired
    private CharacterStateService characterStateService;

    @Autowired
    private ExpService expService;

    @Autowired
    private FormulaService formulaService;

    private Integer lastTotalDmg;

    public void useSpellToEnemy(Integer actionBarNumber, CreepState currentEnemy) {
        Spell usedSpell = getSpellInActionBar(actionBarNumber);
        calculateAfterSpellState(usedSpell, currentEnemy);
        expService.addAttributeExp(usedSpell.getAttribute());
    }

    private Spell getSpellInActionBar(Integer actionBarNumber) {
        return actionBarService.getActionBarMap().get(actionBarNumber);
    }

    private CreepState calculateAfterSpellState(Spell spell, CreepState creepState) {
        calculateDamage(creepState, spell.getDmgCoefficient());
        takeAwayPointCost(spell);
        return creepState;
    }

    private void takeAwayPointCost(Spell spell) {
        String spellType = spell.getSpellType();
        int manaCost = formulaService.getManaCost(spell);

        if (spellType.equals("physical")) {
            characterStateService
                    .getCharacterState()
                    .setCurrentEnergy(characterStateService
                            .getCharacterState()
                            .getCurrentEnergy() - spell.getCost());
        } else {
            characterStateService
                    .getCharacterState()
                    .setCurrentMp(characterStateService
                            .getCharacterState()
                            .getCurrentMp() - manaCost);
        }
    }

    private CreepState calculateDamage(CreepState creepState, int damageCoefficient) {
        damageCoefficient = formulaService.getSpellDamage(damageCoefficient);

        Double minimumDamage = damageCoefficient - damageCoefficient * 0.15;
        Double maximumDamage = damageCoefficient + damageCoefficient * 0.15;

        damageCoefficient = ThreadLocalRandom.current().nextInt(minimumDamage.intValue(),
                maximumDamage.intValue() + 1);

        if (isCritical()) {
            damageCoefficient *= characterStateService.getCharacterState().getCriticalDmgCoefficient();
            LOGGER.info("Critical Strike");
        }

        creepState.setCurrentHp(creepState.getCurrentHp() - damageCoefficient);

        this.lastTotalDmg = damageCoefficient;

        LOGGER.info("Calculated DMG is -> " + damageCoefficient);
        return creepState;
    }


    private boolean isCritical() {
        return new Random().nextInt(100)
                <= characterStateService.getCharacterState().getAgilityProgressLevel();
    }

    public Integer getLastTotalDmg() {
        return lastTotalDmg;
    }

    public boolean manaIsEnough(Integer actionBarNumber) {
        Spell usedSpell = getSpellInActionBar(actionBarNumber);
        String spellType = usedSpell.getSpellType();

        if (spellType.equals("physical")) {
            return characterStateService.getCharacterState().getCurrentEnergy() - usedSpell.getCost() >= 0;
        } else {
            return characterStateService.getCharacterState().getCurrentMp() - formulaService.getManaCost(usedSpell) >= 0;
        }
    }
}
