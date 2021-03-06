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
    <#include "../modules/character.ftl">
    <#include "../modules/combat-text.ftl">
    <#include "../modules/enemies.ftl">
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