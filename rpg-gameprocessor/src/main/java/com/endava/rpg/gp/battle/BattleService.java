package com.endava.rpg.gp.battle;

import com.endava.rpg.gp.battle.location.LocationService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.responsiveness.ResponseService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.util.Refreshable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService implements Refreshable {

    private final SpellService SPELL_SERVICE;

    private final LocationService LOCATION;

    private final CharacterStateService CHARACTER_STATE;

    private final ResponseService RESPONSE;

    private Long battleId;

    @Autowired
    private BattleService(SpellService spellService,
                          LocationService creepLocation,
                          CharacterStateService characterState,
                          ResponseService spellChoice,
                          Refresher refresher) {
        refresher.addRefreshable(this);
        this.SPELL_SERVICE = spellService;
        this.LOCATION = creepLocation;
        this.CHARACTER_STATE = characterState;
        this.RESPONSE = spellChoice;
    }

    public boolean isEndOfBattle() {
        return CHARACTER_STATE.isCharacterDead() || LOCATION.getCreepGroup().size() == 0;
    }

    public void makeATurn(Integer actionBarNumber, CreepState currentEnemy) {
        SPELL_SERVICE.useSpellTo(actionBarNumber, currentEnemy);
        RESPONSE.creepResponse();
        seekDeath();
        useRegeneration();
    }

    public void waitATurn() {
        RESPONSE.creepResponse();
        seekDeath();
        useRegeneration();
    }

    private void useRegeneration() {
        LOCATION.getCreepGroup()
                .forEach(creep -> creep.getPoints().forEach(p -> {
                    if (p.getCurrentValue() < p.getValue()) {
                        p.setCurrentValue(p.getCurrentValue() + p.getRegeneration() >= p.getValue() ?
                                p.getValue() :
                                p.getCurrentValue() + p.getRegeneration());

                    }
                }));

        CHARACTER_STATE.getCharacterState().getPoints().forEach(p -> {
            if (p.getCurrentValue() < p.getValue()) {
                p.setCurrentValue(p.getCurrentValue() + p.getRegeneration() >= p.getValue() ?
                        p.getValue() :
                        p.getCurrentValue() + p.getRegeneration());

            }
        });
    }

    private void seekDeath() {
        if (LOCATION.isCurrentEnemyDead()) {
            LOCATION.getCreepGroup().remove(LOCATION.getCurrentEnemy());
        }
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
}
