<#list actionBar as abNumber, abSpell>
<div class="action-bar-slot">
    <#if abSpell.getSpell().getCurrentCooldown() != 0>
        <div class="cooldown-background"></div>
        <div class="cooldown" title="${abSpell.getDescription()}">${abSpell.getSpell().getCurrentCooldown()}</div>
    </#if>
    <a href="/battle/use-spell/<#if abSpell.getSpell().getSpellURL() != "/resources/img/empty.jpg">${abNumber}<#else>0</#if>">
        <img src="${abSpell.getSpell().getSpellURL()}" class="spell-img" alt="spell-image"
             title="${abSpell.getDescription()}">
    </a>
</div>
</#list>
