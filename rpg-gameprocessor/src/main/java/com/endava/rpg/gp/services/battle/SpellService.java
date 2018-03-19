package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.services.game.Refresher;
import com.endava.rpg.gp.services.state.ActionBarService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.gp.util.CombatTextService;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.gp.util.Refreshable;
import com.endava.rpg.persistence.models.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpellService implements Refreshable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellService.class);

    private final ActionBarService ACTION_BAR_SERVICE;

    private final CharacterStateService CHAR_STATE;

    private final ExpService EXP;

    private final FormulaService FORMULA;

    private Integer lastMovePoints = 0;

    private Integer biggestDmg = 0;

    @Autowired
    private SpellService(ActionBarService actionBarService,
                         CharacterStateService characterStateService,
                         ExpService expService,
                         FormulaService formulaService,
                         Refresher refresher) {

        refresher.addRefreshable(this);
        this.ACTION_BAR_SERVICE = actionBarService;
        this.CHAR_STATE = characterStateService;
        this.EXP = expService;
        this.FORMULA = formulaService;
    }

    public void useSpellTo(Integer actionBarNumber, State target) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        useSpellTo(usedSpell, target, CHAR_STATE.getCharacterState());
    }

    public void useSpellTo(Spell usedSpell, State target, State caster) {
        if (usedSpell.getSpellType().equals("Attack")) {
            int dmg = makeDamage(target, usedSpell.getCoefficient());
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

        if (spellSchool.equals("physical")) {
            return caster.getEnergy().getCurrentValue() - usedSpell.getCost() >= 0;
        } else {
            return caster.getMp().getCurrentValue() - FORMULA.getManaCost(usedSpell) >= 0;
        }
    }

    private Integer protection(State target, int protectionCoefficient) {
        protectionCoefficient = FORMULA.getShield(protectionCoefficient);

        target.setShieldPoints(protectionCoefficient);

        this.lastMovePoints = protectionCoefficient;

        LOGGER.info("Calculated Protection is -> " + protectionCoefficient);

        return protectionCoefficient;
    }

    private Spell getSpellFromActionBar(Integer actionBarNumber) {
        return ACTION_BAR_SERVICE.getActionBarMap().get(actionBarNumber);
    }

    private int takeCost(Spell spell, State caster) {
        String spellType = spell.getSchool();
        int manaCost = FORMULA.getManaCost(spell);

        if (spellType.equals("physical")) {
            caster.getEnergy().setCurrentValue(caster.getEnergy().getCurrentValue() - spell.getCost());
            return spell.getCost();
        } else {
            caster.getMp().setCurrentValue(caster.getMp().getCurrentValue() - manaCost);
            return manaCost;
        }
    }

    //TODO Investigate best approach
    private Integer makeDamage(State target, int damageCoefficient) {
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

        target.getHp().setCurrentValue(target.getHp().getCurrentValue() - damageAfterShield);

        this.lastMovePoints = damageCoefficient;

        biggestDmg = biggestDmg > damageCoefficient ? biggestDmg : damageCoefficient;

        LOGGER.info("Calculated DMG is -> " + damageCoefficient);

        return damageCoefficient;
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

    public List<Spell> getEnemyProtectionSpells(CreepState creep) {
        return getEnemySpells(creep).stream()
                .filter(spell -> spell.getSpellType().equals("Protection"))
                .collect(Collectors.toList());
    }

    public List<Spell> getEnemyAttackSpells(CreepState creep) {
        return getEnemySpells(creep).stream()
                .filter(spell -> spell.getSpellType().equals("Attack"))
                .collect(Collectors.toList());
    }

    public List<Spell> getEnemySpells(CreepState creep) {
        return new ArrayList<>(Stream.of(creep.getSpell_1(), creep.getSpell_2(), creep.getSpell_3())
                .filter(spell -> !spell.getAttribute().equals("none"))
                .collect(Collectors.toList()));
    }

    @Override
    public void refresh() {
        this.lastMovePoints = 0;
        this.biggestDmg = 0;
    }
}
