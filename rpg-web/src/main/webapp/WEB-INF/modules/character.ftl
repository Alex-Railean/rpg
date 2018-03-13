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
    <#list actionBar as abNumber, abSpell>
        <a href="/battle/use-spell/<#if abSpell.getSpellURL() != "/resources/img/empty.jpg">${abNumber}<#else>0</#if>">
            <img src="${abSpell.getSpellURL()}" class="spellImg" alt="spell-image">
        </a>
    </#list>
    </div>
    <div class="bars-exp">
        <div class="strength-exp">
        ${strength}/${strengthNextLevel} (${strengthLevel})
        </div>
        <div class="agility-exp">
        ${agility}/${agilityNextLevel} (${agilityLevel})
        </div>
        <div class="intelligence-exp">
        ${intelligence}/${intelligenceNextLevel} (${intelligenceLevel})
        </div>
        <div><a href="/talents" class="talents-link">Talents</a></div>
    </div>
</div>