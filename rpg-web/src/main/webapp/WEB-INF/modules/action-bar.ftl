<#list actionBar as abNumber, abSpell>
<a href="/battle/use-spell/<#if abSpell.getSpell().getSpellURL() != "/resources/img/empty.jpg">${abNumber}<#else>0</#if>">
    <img src="${abSpell.getSpell().getSpellURL()}" class="spell-img" alt="spell-image"
         title="${abSpell.getDescription()}">
</a>
</#list>
