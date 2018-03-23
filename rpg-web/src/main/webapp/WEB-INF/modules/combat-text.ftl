<div class="combat-text col card bg-light mb-3">
    <div class="card-body">
        <p class="card-text">
        <#list combatText as ct>
        ${ct.getCostMessage()}<br>
        ${ct.getMainMessage()}<br><br>
        </#list>
        </p>
    </div>
</div>
