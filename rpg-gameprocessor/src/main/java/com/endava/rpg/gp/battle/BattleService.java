package com.endava.rpg.gp.battle;

import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.responsiveness.ResponseService;
import com.endava.rpg.gp.state.ActionBarService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.statemodels.points.Point;
import com.endava.rpg.gp.util.Refreshable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService implements Refreshable {

    private static boolean activeTurn = false;

    private final ResponseService RESPONSE;

    private Long battleId;

    @Autowired
    private BattleService(ResponseService spellChoice) {
        Refresher.addRefreshable(this);
        this.RESPONSE = spellChoice;
    }

    public static boolean isActiveTurn() {
        return activeTurn;
    }

    public boolean isEndOfBattle() {
        return CharacterStateService.isCharDead() || EnemyService.getCreepGroup().size() == 0;
    }

    public void makeATurn(Integer actionBarNumber, CreepState currentEnemy) {
        activeTurn = true;
        SpellService.useSpellTo(actionBarNumber, currentEnemy);
        turnActions();
        ActionBarService.tickCooldowns();
        activeTurn = false;
    }

    public void waitATurn() {
        CombatTextService.createWaitMessage(CharacterStateService.getCharacter());
        turnActions();
        ActionBarService.tickCooldowns();
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }

    @Override
    public void refresh() {
        battleId = 0L;
    }

    private void turnActions() {
        applyEffects();
        RESPONSE.creepResponse();
        seekDeath();
        applyRegeneration();
    }

    private void applyEffects() {
        EnemyService.getCreepGroup()
                .forEach(CreepState::useEffects);

        CharacterStateService.getCharacter().useEffects();

        EnemyService.getCreepGroup()
                .forEach(CreepState::applyEffects);

        CharacterStateService.getCharacter().applyEffects();
    }

    private void applyRegeneration() {
        EnemyService.getCreepGroup()
                .forEach(creep -> creep.getPoints().forEach(Point::applyRegeneration));

        CharacterStateService.getCharacter().getPoints().forEach(Point::applyRegeneration);
    }

    private void seekDeath() {
        if (EnemyService.isCurrentEnemyDead()) {
            EnemyService.getCreepGroup().remove(EnemyService.getCurrentEnemy());
        }
    }
}
