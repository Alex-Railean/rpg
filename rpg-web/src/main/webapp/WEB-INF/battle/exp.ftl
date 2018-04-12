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
        <div class="col-sm"></div>
        <div class="col-sm">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">You gain:</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>To strength progress: ${strengthEpx} exp</td>
                </tr>
                <tr>
                    <td>To Agility progress: ${agilityExp} exp</td>
                </tr>
                <tr>
                    <td>To Intelligence progress: ${intelligenceExp} exp</td>
                </tr>
                </tbody>
            </table>
            <a href="/outside">
                <button type="button" class="btn btn-dark">Continue</button>
            </a>
        </div>
        <div class="col-sm"></div>
    </div>
</div>
<script src="/resources/js/main.js"></script>
</body>
</html>