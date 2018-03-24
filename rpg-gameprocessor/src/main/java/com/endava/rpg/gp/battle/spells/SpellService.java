package com.endava.rpg.gp.battle.spells;

import com.endava.rpg.gp.battle.ExpService;
import com.endava.rpg.gp.battle.spells.constants.School;
import com.endava.rpg.gp.battle.spells.constants.SpellType;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.state.ActionBarService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.gp.combattext.CombatTextService;
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

    private final CharacterStateService CHAR_STATE;

    private final ExpService EXP;

    private Integer lastMovePoints = 0;

    private Integer biggestDmg = 0;

    @Autowired
    private SpellService(ActionBarService actionBarService,
                         CharacterStateService characterStateService,
                         ExpService expService,
                         Refresher refresher) {

        refresher.addRefreshable(this);
        this.ACTION_BAR_SERVICE = actionBarService;
        this.CHAR_STATE = characterStateService;
        this.EXP = expService;
    }

    public void useSpellTo(Integer actionBarNumber, State target) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        useSpellTo(usedSpell, target, CHAR_STATE.getCharacterState());
    }

    public void useSpellTo(Spell usedSpell, State target, State caster) {
        if (usedSpell.getSpellType().equals(SpellType.ATTACK)) {
            int dmg = makeDamage(caster, target, usedSpell.getCoefficient());
            int cost = takeCost(usedSpell, caster);
            EXP.addAttributeExp(usedSpell.getAttribute());
            CombatTextService.createSpellRecord(usedSpell, caster, target, dmg, cost);
        } else {
            int protection = protection(caster, usedSpell.getCoefficient());
            int cost = takeCost(usedSpell, caster);
            EXP.addAttributeExp(usedSpell.getAttribute());
            CombatTextService.createSpellRecord(usedSpell, caster, protection, cost);
        }
    }

    public boolean doesHaveEnoughMana(Integer actionBarNumber) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        return isEnoughMana(usedSpell, CHAR_STATE.getCharacterState());
    }

    public boolean isEnoughMana(Spell usedSpell, State caster) {
        String spellSchool = usedSpell.getSchool();

        if (spellSchool.equals(School.PHYSICAL)) {
            return caster.getEnergy().getCurrentValue() - usedSpell.getCost() >= 0;
        } else {
            return caster.getMp().getCurrentValue() - FormulaService.getManaCost(usedSpell) >= 0;
        }
    }

    private Integer protection(State target, int protectionCoefficient) {
        protectionCoefficient = FormulaService.getShield(target, protectionCoefficient);
        target.setShieldPoints(protectionCoefficient);

        if(target instanceof CharacterState) this.lastMovePoints = protectionCoefficient;

        LOGGER.info("Calculated Protection -> " + protectionCoefficient);

        return protectionCoefficient;
    }

    private Spell getSpellFromActionBar(Integer actionBarNumber) {
        return ACTION_BAR_SERVICE.getActionBarMap().get(actionBarNumber).getSpell();
    }

    private int takeCost(Spell spell, State caster) {
        String spellType = spell.getSchool();
        int manaCost = FormulaService.getManaCost(spell);

        if (spellType.equals(School.PHYSICAL)) {
            caster.getEnergy().setCurrentValue(caster.getEnergy().getCurrentValue() - spell.getCost());
            return spell.getCost();
        } else {
            caster.getMp().setCurrentValue(caster.getMp().getCurrentValue() - manaCost);
            return manaCost;
        }
    }

    private Integer makeDamage(State caster, State target, int damageCoefficient) {
        int dmg = FormulaService.getDamage(caster.getLevel(), damageCoefficient);

        Double minimumDamage = FormulaService.getMinDmg(dmg);
        Double maximumDamage = FormulaService.getMaxDmg(dmg);

        dmg = ProcessorUtil.getRandomInt(minimumDamage.intValue(), maximumDamage.intValue() + 1);

        if (isCritical(target)) {
            dmg *= caster.getCriticalDmgCoefficient();
            LOGGER.info("Critical Strike!");
        }

        int damageAfterShield = dmg - target.getShieldPoints() <= 0 ?
                0 : dmg - target.getShieldPoints();

        target.setShieldPoints(target.getShieldPoints() - dmg <= 0 ?
                0 : target.getShieldPoints() - dmg);

        target.getHp().subtractCurrentValue(damageAfterShield);

        if(caster instanceof CharacterState) this.lastMovePoints = dmg;

        if(caster instanceof CharacterState) biggestDmg = biggestDmg < dmg ? dmg : biggestDmg;

        LOGGER.info("Calculated DMG -> " + dmg);

        return dmg;
    }

    private boolean isCritical(State target) {
        return target instanceof CharacterState ?
                new Random().nextInt(100) <= 25 :
                new Random().nextInt(100) <= CHAR_STATE.getCharacterState().getAgility().getProgressLevel();
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
