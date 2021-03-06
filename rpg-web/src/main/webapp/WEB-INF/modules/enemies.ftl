<div class="creeps col">
    <div class="creep col">
        <div class="bars-pints">
            <div class="hp-bar">
                <p>${currentEnemy.getHp().getCurrentValue()}/${currentEnemy.getHp().getValue()}
                <#if currentEnemy.getShieldPoints() != 0>
                    (${currentEnemy.getShieldPoints()})
                </#if>
                </p>
            </div>
            <div class="mana-bar">
                <p>${currentEnemy.getMp().getCurrentValue()}/${currentEnemy.getMp().getValue()}</p>
            </div>
            <div class="energy-bar">
                <p>${currentEnemy.getEnergy().getCurrentValue()}/${currentEnemy.getEnergy().getValue()}</p>
            </div>
        </div>
        <div class="character-image">
            <img src="/resources/img/infectedWolf.jpg" alt="creep-image">
        </div>
    <#list currentEnemy.getEffects() as effect>
        <img src="${effect.getURL()}" alt="effect-image" title="${effect.getDescription()}" class="effect-img">
    </#list>
    </div>
<#list creepsGroup as creep>
    <span> ${creep.getName()}
        [${creep.getLevel()}] (${creep.getHp().getCurrentValue()}/${creep.getHp().getValue()})(${creep.getShieldPoints()}); </span>
</#list>
</div>