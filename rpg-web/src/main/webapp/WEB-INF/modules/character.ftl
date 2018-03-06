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
    <div class="spells">
    <#list actionBar as abNumber, abImage>
        <a href="/battle/use-spell/${abNumber}"><img src="${abImage.getSpellURL()}" class="spellImg" alt="spell-image"></a>
    </#list>
    </div>
    <div class="bars-exp">
        <div class="strength-exp">
        ${strength}/${strengthNextLevel} (${strengthLevel})
            <a href="/character/talents/strength" class="talents-link">Strength Talents</a>
        </div>
        <div class="agility-exp">
        ${agility}/${agilityNextLevel} (${agilityLevel})
            <a href="/character/talents/agility" class="talents-link">Agility Talents</a>
        </div>
        <div class="intelligence-exp">
        ${intelligence}/${intelligenceNextLevel} (${intelligenceLevel})
            <a href="/character/talents/intelligence" class="talents-link">Intelligence Talents</a>
        </div>
    </div>
</div>