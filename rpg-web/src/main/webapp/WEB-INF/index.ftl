<!DOCTYPE html>
<html>
<head>
    <title>Starting location</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
    <a class="navbar-brand" href="/">rpg</a>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-1 col-md-3 col-lg-4"></div>
        <div class="form-wrapper col-xs-10 col-md-6 col-lg-4">
            <form method="POST" action="/">
                <div class="form-group">
                    <label class="form-control-label" for="characterName">Character Name:</label>
                    <input type="text" class="form-control" id="characterName" placeholder="Name" name="characterName">
                </div>
                <button type="submit" class="btn btn-dark">Define Your Character</button>
            </form>
        </div>
    </div>
</div>
<script src="/resources/js/main.js"></script>
</body>
</html>