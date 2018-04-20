package com.endava.rpg.web.controllers.utils;

public final class Paths {

    public static final String
            ROOT = "/",
            API_ROOT = "/api",

    OUTSIDE = "/outside",

    SPELLBOOK = "/spellbook",
            SPELLBOOK_REMOVE = "/spellbook/remove/{slot}",
            SPELLBOOK_SPELL = "/spellbook/{spell}",

    BRANCHES = "/branches",
            BRANCHES_TALENTS = "/branches/{talents}/",
            BRANCHES_UPDATE = "/branches/{talents}/points/{talent}",

    BATTLE = "/battle",
            BATTLE_WAIT = "/battle/wait",
            BATTLE_USE_SPELL = "/battle/use-spell/{actionBarId}",
            TO_BATTLE = "/battle/{battleId}",

    EXP = "/exp",

    HUNGRY_FOREST = "/hungry-forest";

    private Paths() {
    }
}
