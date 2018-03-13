package com.endava.rpg.gp.services.game;

import com.endava.rpg.gp.services.battle.BattleService;
import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.battle.location.LocationService;
import com.endava.rpg.gp.services.battle.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Refresher {

    @Autowired
    private Refresher(BattleService battle, ExpService exp, LocationService location, SpellService spellService) {
        this.battle = battle;
        this.exp = exp;
        this.location = location;
        this.spellService = spellService;
    }

    private final BattleService battle;

    private final ExpService exp;

    private final LocationService location;

    private final SpellService spellService;

    public void refresh() {
        battle.refresh();
        exp.refresh();
        location.refresh();
        spellService.refresh();
    }
}
