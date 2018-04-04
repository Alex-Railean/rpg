package com.endava.rpg.gp.battle.spells;

import com.endava.rpg.gp.battle.ExpService;
import com.endava.rpg.gp.battle.spells.constants.School;
import com.endava.rpg.gp.battle.spells.constants.SpellType;
import com.endava.rpg.gp.battle.spells.effects.shields.Shield;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.state.ActionBarService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.persistence.models.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SpellService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellService.class);

    public static void useSpellTo(Integer actionBarNumber, State target) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        useSpellTo(CharacterStateService.getCharacter(), target, usedSpell);
    }

    public static void useSpellTo(State caster, State target, Spell usedSpell) {
        if (usedSpell.getSpellType().equals(SpellType.ATTACK)) {
            int dmg = makeDamage(caster, target, usedSpell.getCoefficient());
            int cost = takeCost(usedSpell, caster);
            ExpService.addAttributeExp(usedSpell.getAttribute());
            CombatTextService.createAttackRecord(usedSpell, caster, target, dmg, cost);
        } else if (usedSpell.getSpellType().equals(SpellType.PROTECTION)) {
            int protection = protection(caster, usedSpell);
            int cost = takeCost(usedSpell, caster);
            ExpService.addAttributeExp(usedSpell.getAttribute());
            CombatTextService.createShieldRecord(usedSpell, caster, protection, cost);
        }
    }

    public static boolean isEnoughMana(Spell usedSpell, State caster) {
        String spellSchool = usedSpell.getSchool();

        if (spellSchool.equals(School.PHYSICAL)) {
            return caster.getEnergy().getCurrentValue() - usedSpell.getCost() >= 0;
        } else {
            return caster.getMp().getCurrentValue() - FormulaService.getManaCost(usedSpell) >= 0;
        }
    }

    public static void damageIt(State target, int dmg) {
        Shield shield;

        do {
            shield = target.getRandomShield();

            if (shield != null) {
                int shieldPoints = shield.getPoints();

                if (shieldPoints - dmg <= 0) {
                    shield.setPoints(0);
                    dmg -= shieldPoints;
                } else {
                    shield.setPoints(shieldPoints - dmg);
                    dmg = 0;
                }
            }

        } while (dmg != 0 && shield != null && shield.getPoints() != 0);

        target.getHp().subtractCurrentValue(dmg);
    }

    private static Spell getSpellFromActionBar(Integer actionBarNumber) {
        return ActionBarService.getActionBarMap().get(actionBarNumber).getSpell();
    }

    private static int protection(State target, Spell shieldSpell) {
        Shield shield = (Shield) target.addEffect(target, shieldSpell);
        int shieldPoints = shield.getPoints();

        if (target instanceof CharacterState) CharacterStateService.getCharacter().setLastMovePoints(shieldPoints);

        LOGGER.info("Calculated Protection -> " + shieldPoints);

        return shieldPoints;
    }

    private static int takeCost(Spell spell, State caster) {
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

    private static Integer makeDamage(State caster, State target, int damageCoefficient) {
        int dmg = FormulaService.getDamage(caster.getLevel(), damageCoefficient);
        CharacterState character = CharacterStateService.getCharacter();

        Double minimumDamage = FormulaService.getMinDmg(dmg);
        Double maximumDamage = FormulaService.getMaxDmg(dmg);

        dmg = ProcessorUtil.getRandomInt(minimumDamage.intValue(), maximumDamage.intValue() + 1);

        if (isCritical(target)) {
            dmg *= caster.getCriticalDmgCoefficient();
            LOGGER.info("Critical Strike!");
        }

        int dmgForLog = dmg;

        damageIt(target, dmg);

        if (caster instanceof CharacterState) character.setLastMovePoints(dmgForLog);

        if (caster instanceof CharacterState && character.getBiggestDmg() < dmgForLog)
            character.setBiggestDmg(dmgForLog);

        LOGGER.info("Calculated DMG -> " + dmgForLog);

        return dmgForLog;
    }

    private static boolean isCritical(State target) {
        return target instanceof CharacterState ?
                new Random().nextInt(100) <= 25 :
                new Random().nextInt(100) <= CharacterStateService.getCharacter().getAgility().getProgressLevel();
    }

    public boolean doesHaveEnoughMana(Integer actionBarNumber) {
        Spell usedSpell = getSpellFromActionBar(actionBarNumber);
        return isEnoughMana(usedSpell, CharacterStateService.getCharacter());
    }
}
