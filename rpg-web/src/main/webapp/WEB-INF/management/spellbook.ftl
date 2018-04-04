<!DOCTYPE html>
<html>
<head>
    <title>Starting location</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<#include "../modules/header.ftl">
<div class="container">
    <div class="row">
        <div class="spells">
        <#list spellBookContent as spell>
            <form action="/spellbook/${spell.getSpell().getSpellName()}" method="POST" class="spellbook-form">
                <img src="${spell.getSpell().getSpellURL()}" class="spell-img" alt="spell-image"
                     title="${spell.getDescription()}">
                <select class="custom-select mb-2 mr-sm-2 mb-sm-0 slot" id="inlineFormCustomSelect" name="slot">
                    <#list actionBar as abNumber, abImage>
                        <option value="${abNumber}">${abNumber}</option>
                    </#list>
                </select>
                <button type="submit" class="btn btn-dark">+</button>
            </form>
        </#list>
        </div>
    </div>
    <div class="row">
        <div class="spells">
        <#list actionBar as abNumber, abSpell>
            <a href="/spellbook/remove/${abNumber}">
                <img src="${abSpell.getSpell().getSpellURL()}" class="spell-img" alt="spell-image"
                     title="${abSpell.getDescription()}">
            </a>
        </#list>
        </div>
    </div>
    <a href="/outside">
        <button type="button" class="btn btn-dark bottom-button">To Outside</button>
    </a>
<#if warningMessage??>
    <div class="alert alert-primary" role="alert">
    ${warningMessage}
    </div>
</#if>
</div>
<script src="/resources/js/main.js"></script>
</body>
</html>