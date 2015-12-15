<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Airtng - The Next Generation of Vacation rentals</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw=="
          crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha256-k2/8zcNbxVIh5mnQ52A0r3a6jAgMGxFJFE2707UxGCk= sha512-ZV9KawG2Legkwp3nAlxLIVFudTauWuBpC10uEafMHYL0Sarrz5A7G79kXh5+5+woxQ5HM559XX2UZjMJ36Wplg=="
          crossorigin="anonymous">

    <link href="css/main.css" rel="stylesheet"/>
    <link href="css/scaffolds.css" rel="stylesheet"/>
    <link href="css/vacation_properties.css" rel="stylesheet"/>
    <link href="css/site.css" rel="stylesheet"/>

    <link rel="stylesheet" media="all"
          href="http://cdnjs.cloudflare.com/ajax/libs/authy-forms.css/2.2/form.authy.min.css"/>
</head>
<body>

<!-- Nav Bar -->
<nav class="navbar navbar-space">
</nav>

<nav class="navbar navbar-transparent">
    <a class="navbar-brand" href="/">airtng</a>
    <ul class="navbar-nav navbar-right pull-right">
        <li><a href="/Account/Register" id="registerLink">Sign Up</a></li>
        <li><a href="/Account/Login" id="loginLink">Log In</a></li>

    </ul>
</nav>


<section id="main" class="push-nav">

    <div class="container">

        <h1>Log in</h1>

        <form action="/login" method="post">
            <div class="form-group">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" name="email" id="email" placeholder="Email"
                           value="${email}">
                    <span class="text-danger">${emailError}</span>
                    <span class="text-danger">${emailInvalidError}</span>
                    <span class="text-danger">${loginError}</span>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password" id="password" value="${password}"
                           placeholder="Password">
                    <span class="text-danger">${passwordError}</span>
                </div>
            </div>
            <input type="submit" value="Log In" class="btn btn-primary"/>
        </form>
    </div>
</section>
<footer class="container">
    Made with <i class="fa fa-heart"></i> by your pals
    <a href="http://www.twilio.com">@twilio</a>
</footer>

<script src="//cdnjs.cloudflare.com/ajax/libs/authy-forms.js/2.2/form.authy.min.js"></script>
</body>
</html>