package com.endava.rpg.persistence.models;

import javax.persistence.*;

@Entity
@Table(name = "T_ACTION_BAR")
public class ActionBar implements TableMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ACTION_BAR_ID")
    private Integer actionBarId;

    @ManyToOne
    @JoinColumn(name = "SPELL_1_ID")
    private Spell spell_1;

    @ManyToOne
    @JoinColumn(name = "SPELL_2_ID")
    private Spell spell_2;

    @ManyToOne
    @JoinColumn(name = "SPELL_3_ID")
    private Spell spell_3;

    @ManyToOne
    @JoinColumn(name = "SPELL_4_ID")
    private Spell spell_4;

    @ManyToOne
    @JoinColumn(name = "SPELL_5_ID")
    private Spell spell_5;

    @ManyToOne
    @JoinColumn(name = "SPELL_6_ID")
    private Spell spell_6;

    @ManyToOne
    @JoinColumn(name = "SPELL_7_ID")
    private Spell spell_7;

    @ManyToOne
    @JoinColumn(name = "SPELL_8_ID")
    private Spell spell_8;

    @ManyToOne
    @JoinColumn(name = "SPELL_9_ID")
    private Spell spell_9;

    @ManyToOne
    @JoinColumn(name = "SPELL_10_ID")
    private Spell spell_10;

    @ManyToOne
    @JoinColumn(name = "SPELL_11_ID")
    private Spell spell_11;

    @ManyToOne
    @JoinColumn(name = "SPELL_12_ID")
    private Spell spell_12;

    public Integer getActionBarId() {
        return actionBarId;
    }

    public Spell getSpell_1() {
        return spell_1;
    }

    public ActionBar setSpell_1(Spell spellId_1) {
        this.spell_1 = spellId_1;
        return this;
    }

    public Spell getSpell_2() {
        return spell_2;
    }

    public ActionBar setSpell_2(Spell spellId_2) {
        this.spell_2 = spellId_2;
        return this;
    }

    public Spell getSpell_3() {
        return spell_3;
    }

    public ActionBar setSpell_3(Spell spellId_3) {
        this.spell_3 = spellId_3;
        return this;
    }

    public Spell getSpell_4() {
        return spell_4;
    }

    public ActionBar setSpell_4(Spell spellId_4) {
        this.spell_4 = spellId_4;
        return this;
    }

    public Spell getSpell_5() {
        return spell_5;
    }

    public ActionBar setSpell_5(Spell spellId_5) {
        this.spell_5 = spellId_5;
        return this;
    }

    public Spell getSpell_6() {
        return spell_6;
    }

    public ActionBar setSpell_6(Spell spellId_6) {
        this.spell_6 = spellId_6;
        return this;
    }

    public Spell getSpell_7() {
        return spell_7;
    }

    public ActionBar setSpell_7(Spell spellId_7) {
        this.spell_7 = spellId_7;
        return this;
    }

    public Spell getSpell_8() {
        return spell_8;
    }

    public ActionBar setSpell_8(Spell spellId_8) {
        this.spell_8 = spellId_8;
        return this;
    }

    public Spell getSpell_9() {
        return spell_9;
    }

    public ActionBar setSpell_9(Spell spellId_9) {
        this.spell_9 = spellId_9;
        return this;
    }

    public Spell getSpell_10() {
        return spell_10;
    }

    public ActionBar setSpell_10(Spell spellId_10) {
        this.spell_10 = spellId_10;
        return this;
    }

    public Spell getSpell_11() {
        return spell_11;
    }

    public ActionBar setSpell_11(Spell spellId_11) {
        this.spell_11 = spellId_11;
        return this;
    }

    public Spell getSpell_12() {
        return spell_12;
    }

    public ActionBar setSpell_12(Spell spellId_12) {
        this.spell_12 = spellId_12;
        return this;
    }
}
