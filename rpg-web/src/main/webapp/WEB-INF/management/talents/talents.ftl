<!DOCTYPE html>
<html>
<head>
    <title>Starting location</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<#include "../../modules/header.ftl">

<#list branches as branch>
<p>${branch.getName()}</p>
<a href="/talents/${branch.getLinkName()}/"><img src="${branch.getURL()}" alt="branch-image"></a>
</#list>
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