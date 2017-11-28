package com.endava.rpg.gp.services.game;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Integer growthFactor = 7;

    private Integer gameRate = 1;

    public Integer getGrowthFactor() {
        return growthFactor;
    }

    public GameService setGrowthFactor(Integer growthFactor) {
        this.growthFactor = growthFactor;
        return this;
    }

    public Integer getGameRate() {
        return gameRate;
    }

    public GameService setGameRate(Integer gameRate) {
        this.gameRate = gameRate;
        return this;
    }
}
