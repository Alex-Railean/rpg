<div class="character col">
    <div class="bars-pints">
        <div class="hp-bar">
            <p>${currentHp}/${hp}
            <#if shield != 0>
                (${shield})
            </#if>
            </p>
        </div>
        <div class="mana-bar">
            <p>${currentMp}/${mp}</p>
        </div>
        <div class="energy-bar">
            <p>${currentEnergy}/${energy}</p>
        </div>
    </div>
    <div class="character-image">
        <img src="/resources/img/testCharacter.jpg" alt="character-image">
    </div>
<#list effects as effect>
    <img src="${effect.getURL()}" alt="effect-image" title="${effect.getDescription()}" class="effect-img">
</#list>
    <div class="spells">
    <#include "action-bar.ftl">
    </div>
    <div class="bars-exp">
        <div class="strength-exp">
        ${strength}/${strengthNextLevel} (${strengthLevel})
        </div>
        <div class="agility-exp">
        ${agility}/${agilityNextLevel} (${agilityLevel})
        </div>
        <div class="intellect-exp">
        ${intellect}/${intellectNextLevel} (${intellectLevel})
        </div>
        <div><a href="/branches" class="talents-link">Talents (${freePoints})</a></div>
    </div>
<#if combatText??>
    <a href="/battle/wait">
        <button type="button" class="btn btn-dark bottom-button">Wait</button>
    </a>
</#if>
</div>