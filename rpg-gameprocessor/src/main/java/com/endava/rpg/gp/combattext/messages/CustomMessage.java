package com.endava.rpg.gp.combattext.messages;

public class CustomMessage implements CombatMessage {
    private String costMessage;

    private String mainMessage;

    public CustomMessage(String costMessage, String mainMessage) {
        this.costMessage = costMessage;
        this.mainMessage = mainMessage;
    }

    @Override
    public String getCostMessage() {
        return costMessage;
    }

    @Override
    public String getMainMessage() {
        return mainMessage;
    }
}
