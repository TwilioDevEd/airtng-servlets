<%@ page import="static org.twilio.airtng.lib.auth.SessionManager.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

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

<%
    Boolean authenticated = (Boolean) session.getAttribute("authenticated");
%>

<layout:block name="nav_bg">

</layout:block>

<!-- Nav Bar -->
<nav class="navbar navbar-transparent">
    <a class="navbar-brand" href="/">airtng</a>
    <ul class="navbar-nav navbar-right pull-right">
        <core:choose>
            <core:when test="${authenticated}">
                <form action="/logout" method="POST" id="logoutForm" style="display:none;"></form>
                <li><img src="/images/spock.png" alt="Spock"/></li>
                <li><a href="/properties-new" id="newPropertyLink">New Vacation property</a></li>
                <li><a href="javascript:document.getElementById('logoutForm').submit()" id="logoutLink">Log out</a></li>
            </core:when>
            <core:otherwise>
                <li><a href="/register" id="registerLink">Sign Up</a></li>
                <li><a href="/login" id="loginLink">Log In</a></li>
            </core:otherwise>
        </core:choose>
    </ul>

</nav>

<layout:block name="hero">

</layout:block>


<section id="main" class="push-nav">
    <layout:block name="contents">

    </layout:block>
</section>

<footer class="container">
    Made with <i class="fa fa-heart"></i> by your pals
    <a href="http://www.twilio.com">@twilio</a>
</footer>

<script src="//cdnjs.cloudflare.com/ajax/libs/authy-forms.js/2.2/form.authy.min.js"></script>
</body>
</html>