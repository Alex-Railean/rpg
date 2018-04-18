package com.endava.rpg.gp.responsiveness;

import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//TODO: Upgrade creep brain
@Service
public class ResponseService {

    public void creepResponse() {
        for (CreepState c : EnemyService.getCreepGroup()) {
            if (isAlmostDead(c)) {
                List<Spell> spells = getEnemyProtectionSpells(c);
                Spell toCast;
                if (SpellService.isEnoughMana(toCast = chooseAppropriate(spells), c)) {
                    SpellService.useSpellTo(c, CharacterStateService.getCharacter(), toCast);
                } else if (chooseAnotherOption(c.getSpells()) != null) {
                    SpellService.useSpellTo(c, CharacterStateService.getCharacter(), chooseAnotherOption(c.getSpells()));
                } else {
                    CombatTextService.createWaitMessage(c);
                }

            } else {
                Spell strongest = chooseStrongestSpell(getEnemyAttackSpells(c));
                if (SpellService.isEnoughMana(strongest, c)) {
                    SpellService.useSpellTo(c, CharacterStateService.getCharacter(), strongest);
                } else {
                    CombatTextService.createWaitMessage(c);
                }
            }
        }
    }

    private Spell chooseStrongestSpell(List<Spell> spells) {
        return spells.stream()
                .max(Comparator.comparing(Spell::getCoefficient))
                .orElse(null);
    }

    private Spell chooseAnotherOption(List<Spell> spells) {
        return spells.stream()
                .filter(spell -> SpellService.isEnoughMana(spell, EnemyService.getCurrentEnemy()))
                .findFirst()
                .orElse(null);
    }

    private Spell chooseAppropriate(List<Spell> spells) {
        Spell strongest = chooseStrongestSpell(spells);

        Spell weakest = spells.stream()
                .min(Comparator.comparing(Spell::getCoefficient))
                .orElse(null);

        return isRisky() ?
                SpellService.isEnoughMana(strongest, EnemyService.getCurrentEnemy()) ? strongest : weakest
                : weakest;
    }

    private boolean isRisky() {
        return EnemyService.getCreepGroup().size() <= 4;
    }

    // TODO: Move risk coefficient (1.8)
    private boolean isAlmostDead(CreepState creep) {
        return !creep.getHp().getValue().equals(creep.getHp().getCurrentValue()) &&
                creep.getHp().getCurrentValue() +
                        EnemyService.getCurrentEnemy().getShieldPoints() <
                        CharacterStateService.getCharacter().getBiggestDmg() * 1.8;
    }

    private List<Spell> getEnemyProtectionSpells(CreepState creep) {
        return creep.getSpells().stream()
                .filter(SpellService::isProtectionSpell)
                .collect(Collectors.toList());
    }

    private List<Spell> getEnemyAttackSpells(CreepState creep) {
        return creep.getSpells().stream()
                .filter(SpellService::isAttackSpell)
                .collect(Collectors.toList());
    }
}
