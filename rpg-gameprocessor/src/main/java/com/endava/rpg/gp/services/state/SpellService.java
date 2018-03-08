package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.gp.util.Refreshable;
import com.endava.rpg.persistence.models.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SpellService implements Refreshable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellService.class);

    private final ActionBarService ACTION_BAR_SERVICE;

    private final CharacterStateService CHAR_STATE_SERVICE;

    private final ExpService EXP;

    private final FormulaService FORMULA;

    private Integer lastMovePoints;

    private Integer biggestDmg = 0;

    @Autowired
    private SpellService(ActionBarService actionBarService,
                        CharacterStateService characterStateService,
                        ExpService expService,
                        FormulaService formulaService) {
        this.ACTION_BAR_SERVICE = actionBarService;
        this.CHAR_STATE_SERVICE = characterStateService;
        this.EXP = expService;
        this.FORMULA = formulaService;
    }

    public void useSpellTo(Integer actionBarNumber, State target) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        useSpellTo(usedSpell, target, CHAR_STATE_SERVICE.getCharacterState());
    }

    public void useSpellTo(Spell usedSpell, State target, State caster) {
        if (usedSpell.getSpellType().equals("Attack")) {
            makeDamage(target, usedSpell.getCoefficient());
            takeCost(usedSpell, caster);
            EXP.addAttributeExp(usedSpell.getAttribute());
        } else {
            protection(caster, usedSpell.getCoefficient());
            takeCost(usedSpell, caster);
            EXP.addAttributeExp(usedSpell.getAttribute());
        }
    }

    public boolean doesHaveEnoughMana(Integer actionBarNumber) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        return isManaEnough(usedSpell, CHAR_STATE_SERVICE.getCharacterState());
    }

    public boolean isManaEnough(Spell usedSpell, State caster) {
        String spellSchool = usedSpell.getSchool();

        if (spellSchool.equals("physical")) {
            return caster.getCurrentEnergy() - usedSpell.getCost() >= 0;
        } else {
            return caster.getCurrentMp() - FORMULA.getManaCost(usedSpell) >= 0;
        }
    }

    private void protection(State target, int protectionCoefficient) {
        protectionCoefficient = FORMULA.getShield(protectionCoefficient);

        target.setShieldPoints(protectionCoefficient);

        this.lastMovePoints = protectionCoefficient;

        LOGGER.info("Calculated Protection is -> " + protectionCoefficient);
    }

    private Spell getSpellFromActionBar(Integer actionBarNumber) {
        return ACTION_BAR_SERVICE.getActionBarMap().get(actionBarNumber);
    }

    private void takeCost(Spell spell, State caster) {
        String spellType = spell.getSchool();
        int manaCost = FORMULA.getManaCost(spell);

        if (spellType.equals("physical")) {
            caster.setCurrentEnergy(caster.getCurrentEnergy() - spell.getCost());
        } else {
            caster.setCurrentMp(caster.getCurrentMp() - manaCost);
        }
    }

    //TODO Investigate best approach
    private void makeDamage(State target, int damageCoefficient) {
        damageCoefficient = FORMULA.getDamage(damageCoefficient);

        Double minimumDamage = damageCoefficient - damageCoefficient * 0.15;
        Double maximumDamage = damageCoefficient + damageCoefficient * 0.15;

        damageCoefficient = ProcessorUtil.getRandomInt(minimumDamage.intValue(), maximumDamage.intValue() + 1);

        if (isCritical(target)) {
            damageCoefficient *= target.getCriticalDmgCoefficient();
            LOGGER.info("Critical Strike");
        }

        int damageAfterShield = damageCoefficient - target.getShieldPoints() <= 0 ?
                0 : damageCoefficient - target.getShieldPoints();

        target.setShieldPoints(target.getShieldPoints() - damageCoefficient <= 0 ?
                0 : target.getShieldPoints() - damageCoefficient);

        target.setCurrentHp(target.getCurrentHp() - damageAfterShield);

        this.lastMovePoints = damageCoefficient;

        biggestDmg = biggestDmg > damageCoefficient ? biggestDmg : damageCoefficient;

        LOGGER.info("Calculated DMG is -> " + damageCoefficient);
    }


    private boolean isCritical(State target) {
        return target instanceof CharacterState ?
                new Random().nextInt(100) <= 25 :
                new Random().nextInt(100) <= CHAR_STATE_SERVICE.getCharacterState().getAgilityProgressLevel();
    }

    public Integer getLastMovePoints() {
        return lastMovePoints;
    }

    public Integer getBiggestDmg() {
        return biggestDmg;
    }

    @Override
    public void refresh() {
        this.lastMovePoints = 0;
        this.biggestDmg = 0;
    }
}
