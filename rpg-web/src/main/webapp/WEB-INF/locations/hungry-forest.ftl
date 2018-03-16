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
        <div class="creeps col-6">
            <a href="/battle">
                <button type="submit" class="btn btn-danger">To Battle!</button>
            </a>
        <#list creepsGroup as creep>
            <span> ${creep.getCreepName()}
                [${creep.getCreepLevel()}] (${creep.getHp().getCurrentValue()}/${creep.getHp().getValue()}); </span>
        </#list>
        </div>
    </div>
</div>
<script src="/resources/js/main.js"></script>
</body>
</html>