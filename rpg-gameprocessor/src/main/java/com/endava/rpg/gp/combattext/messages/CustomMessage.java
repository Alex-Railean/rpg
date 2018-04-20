package com.endava.rpg.gp.combattext.messages;

public class CustomMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public CustomMessage(String headerMessage, String mainMessage) {
        this.headerMessage = headerMessage;
        this.mainMessage = mainMessage;
    }

    @Override
    public String getHeaderMessage() {
        return headerMessage;
    }

    @Override
    public String getMainMessage() {
        return mainMessage;
    }
}
