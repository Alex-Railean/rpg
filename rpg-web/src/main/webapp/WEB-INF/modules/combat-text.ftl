<div class="combat-text col card bg-light mb-3">
    <div class="card-body">
        <p class="card-text">
        <#list combatText as ct>
            <#if ct.getHeaderMessage()??>
            ${ct.getHeaderMessage()}<br>
            </#if>
            <#if ct.getMainMessage()??>
            ${ct.getMainMessage()}<br><br>
            </#if>
        </#list>
        </p>
    </div>
</div>
