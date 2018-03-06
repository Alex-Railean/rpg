<div class="creeps col">
    <div class="creep col">
        <div class="bars-pints">
            <div class="hp-bar">
                <p>${currentEnemy.getCurrentHp()}/${currentEnemy.getHp()}
                <#if currentEnemy.getShieldPoints() != 0>
                    (${currentEnemy.getShieldPoints()})
                </#if>
                </p>
            </div>
            <div class="mana-bar">
                <p>${currentEnemy.getCurrentMp()}/${currentEnemy.getMp()}</p>
            </div>
            <div class="energy-bar">
                <p>${currentEnemy.getCurrentEnergy()}/${currentEnemy.getEnergy()}</p>
            </div>
        </div>
        <div class="character-image">
            <img src="/resources/img/infectedWolf.jpg" alt="creep-image">
        </div>
    </div>
<#list creepsGroup as creep>
    <span> ${creep.getCreepName()}
        [${creep.getCreepLevel()}] (${creep.getCurrentHp()}/${creep.getHp()})(${creep.getShieldPoints()}); </span>
</#list>
</div>