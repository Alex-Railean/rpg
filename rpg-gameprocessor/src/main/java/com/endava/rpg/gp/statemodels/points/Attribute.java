package com.endava.rpg.gp.statemodels.points;

import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.statemodels.CharacterState;

import java.io.Serializable;

public class Attribute implements Serializable {

    private Integer progressLevel;

    private Integer toNextLevel;

    private Integer progress;

    public Attribute(CharacterState cs) {
        cs.addAttribute(this);
    }

    public boolean isItNextLevel(Integer additionalExp) {
        return progress + additionalExp >= toNextLevel;
    }

    public boolean isLevelStable() {
        return progress < toNextLevel;
    }

    public Attribute addProgressLevel(Integer progressLevel) {
        this.progressLevel += progressLevel;
        this.toNextLevel = FormulaService.getNextLevelExp(progressLevel);
        return this;
    }

    public Attribute addProgress(Integer progress) {
        this.progress += progress;
        return this;
    }

    public Integer getProgressLevel() {
        return progressLevel;
    }

    public Attribute setProgressLevel(Integer progressLevel) {
        this.progressLevel = progressLevel;
        this.toNextLevel = FormulaService.getNextLevelExp(progressLevel);
        return this;
    }

    public Integer getToNextLevel() {
        return toNextLevel;
    }

    public Integer getProgress() {
        return progress;
    }

    public Attribute setProgress(Integer progress) {
        this.progress = progress;
        return this;
    }
}
