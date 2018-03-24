<#list actionBar as abNumber, abSpell>
<span class="popup" data-popuptext="${abSpell.getDescription()}">
<a href="/battle/use-spell/<#if abSpell.getSpell().getSpellURL() != "/resources/img/empty.jpg">${abNumber}<#else>0</#if>">
    <img src="${abSpell.getSpell().getSpellURL()}" class="spell-img popup" alt="spell-image"">
</a>
</span>
</#list>
