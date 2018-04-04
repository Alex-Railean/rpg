package com.endava.rpg.persistence.models;

import com.endava.rpg.persistence.services.utils.EffectData;

import javax.persistence.*;

@Entity
@Table(name = "T_EFFECT")
public class EffectCore implements TableMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EFFECT_ID")
    private Integer effectId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "URL")
    private String URL;

    @Transient
    private boolean modified = false;

    public EffectCore modify() {
        this.modified = true;
        return this;
    }

    public Integer getEffectId() {
        return effectId;
    }

    public String getName() {
        return name;
    }

    public EffectCore setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        if (!this.modified) {
            return EffectData.modify(this).getDescription();
        }
        return description;
    }

    public EffectCore setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public EffectCore setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getURL() {
        return URL;
    }

    public EffectCore setURL(String URL) {
        this.URL = URL;
        return this;
    }
}
