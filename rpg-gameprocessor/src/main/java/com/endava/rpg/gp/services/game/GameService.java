package com.endava.rpg.gp.services.game;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final Integer GROWTH_FACTOR = 7;

    private final Integer GAME_RATE = 1;

    public Integer getGrowthFactor() {
        return GROWTH_FACTOR;
    }

    public Integer getGameRate() {
        return GAME_RATE;
    }

}
