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
            <form action="/spellbook/${spell.getSpellName()}" method="POST" class="spellbook-form">
                <img src="${spell.getSpellURL()}" class="spellImg" alt="spell-image">
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
        <#list actionBar as abNumber, abImage>
            <a href="/spellbook/remove/${abNumber}"><img src="${abImage.getSpellURL()}" class="spellImg" alt="spell-image"></a>
        </#list>
        </div>
    </div>
    <a href="/outside"><button type="button" class="btn btn-dark bottom-button">To Outside</button></a>
<#if warningMessage??>
    <div class="alert alert-primary" role="alert">
    ${warningMessage}
    </div>
</#if>
</div>
<script src="/resources/js/main.js"></script>
</body>
</html>