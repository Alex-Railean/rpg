<!DOCTYPE html>
<html>
<head>
    <title>Starting location</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<#include "../../modules/header.ftl">
<div class="container">
    <h3>${branch.getName()}</h3>
    <div class="talents">
    <#list branch.getTalents() as talent>
        <div class="talent">
            <div>
                <img src="${talent.getURL()}" alt="talent-image" class="talent-img" title="${talent.getDescription()}">
            </div>
            <p>${talent.getPoints()}/${talent.getLimit()}</p>
            <form action="points/${talent.getLinkName()}" method="POST">
                <input class="form-control talent-counter" type="number" value="0" id="example-number-input"
                       max="${talent.getLimit() - talent.getPoints()}" min="0" name="points">
                <button type="submit" class="btn btn-dark points-button">+</button>
            </form>
        </div>
    </#list>
    </div>
    <h4>Free Points: ${freePoints}</h4>
    <div>
        <a href="/outside">
            <button type="button" class="btn btn-dark bottom-button">To Outside</button>
        </a>
    </div>
<#if warningMessage??>
    <div class="alert alert-primary" role="alert">
    ${warningMessage}
    </div>
</#if>
</div>
<script src="/resources/js/main.js"></script>
</body>
</html>