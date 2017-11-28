package com.endava.rpg.persistence.models;

import javax.persistence.*;

@Entity
@Table(name = "T_ACTION_BAR")
public class ActionBar implements Models{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ACTION_BAR_ID")
    private Integer actionBarId;

    @ManyToOne
    @JoinColumn(name = "SPELL_1_ID")
    private Spell spellId_1;

    @ManyToOne
    @JoinColumn(name = "SPELL_2_ID")
    private Spell spellId_2;

    @ManyToOne
    @JoinColumn(name = "SPELL_3_ID")
    private Spell spellId_3;

    @ManyToOne
    @JoinColumn(name = "SPELL_4_ID")
    private Spell spellId_4;

    @ManyToOne
    @JoinColumn(name = "SPELL_5_ID")
    private Spell spellId_5;

    @ManyToOne
    @JoinColumn(name = "SPELL_6_ID")
    private Spell spellId_6;

    @ManyToOne
    @JoinColumn(name = "SPELL_7_ID")
    private Spell spellId_7;

    @ManyToOne
    @JoinColumn(name = "SPELL_8_ID")
    private Spell spellId_8;

    @ManyToOne
    @JoinColumn(name = "SPELL_9_ID")
    private Spell spellId_9;

    @ManyToOne
    @JoinColumn(name = "SPELL_10_ID")
    private Spell spellId_10;

    @ManyToOne
    @JoinColumn(name = "SPELL_11_ID")
    private Spell spellId_11;

    @ManyToOne
    @JoinColumn(name = "SPELL_12_ID")
    private Spell spellId_12;

    public Integer getActionBarId() {
        return actionBarId;
    }

    public Spell getSpellId_1() {
        return spellId_1;
    }

    public ActionBar setSpellId_1(Spell spellId_1) {
        this.spellId_1 = spellId_1;
        return this;
    }

    public Spell getSpellId_2() {
        return spellId_2;
    }

    public ActionBar setSpellId_2(Spell spellId_2) {
        this.spellId_2 = spellId_2;
        return this;
    }

    public Spell getSpellId_3() {
        return spellId_3;
    }

    public ActionBar setSpellId_3(Spell spellId_3) {
        this.spellId_3 = spellId_3;
        return this;
    }

    public Spell getSpellId_4() {
        return spellId_4;
    }

    public ActionBar setSpellId_4(Spell spellId_4) {
        this.spellId_4 = spellId_4;
        return this;
    }

    public Spell getSpellId_5() {
        return spellId_5;
    }

    public ActionBar setSpellId_5(Spell spellId_5) {
        this.spellId_5 = spellId_5;
        return this;
    }

    public Spell getSpellId_6() {
        return spellId_6;
    }

    public ActionBar setSpellId_6(Spell spellId_6) {
        this.spellId_6 = spellId_6;
        return this;
    }

    public Spell getSpellId_7() {
        return spellId_7;
    }

    public ActionBar setSpellId_7(Spell spellId_7) {
        this.spellId_7 = spellId_7;
        return this;
    }

    public Spell getSpellId_8() {
        return spellId_8;
    }

    public ActionBar setSpellId_8(Spell spellId_8) {
        this.spellId_8 = spellId_8;
        return this;
    }

    public Spell getSpellId_9() {
        return spellId_9;
    }

    public ActionBar setSpellId_9(Spell spellId_9) {
        this.spellId_9 = spellId_9;
        return this;
    }

    public Spell getSpellId_10() {
        return spellId_10;
    }

    public ActionBar setSpellId_10(Spell spellId_10) {
        this.spellId_10 = spellId_10;
        return this;
    }

    public Spell getSpellId_11() {
        return spellId_11;
    }

    public ActionBar setSpellId_11(Spell spellId_11) {
        this.spellId_11 = spellId_11;
        return this;
    }

    public Spell getSpellId_12() {
        return spellId_12;
    }

    public ActionBar setSpellId_12(Spell spellId_12) {
        this.spellId_12 = spellId_12;
        return this;
    }
}
