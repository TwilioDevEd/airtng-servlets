<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>

<layout:extends name="base">
    <layout:put block="nav_bg" type="REPLACE">
        <!-- Nav Bar -->
        <nav class="navbar navbar-space">
        </nav>
    </layout:put>
    <layout:put block="contents" type="REPLACE">
        <div class="container">
            <h1>Signup for Airtng</h1>

            <form action="/register" method="post">
                <div class="form-group">
                    <label for="name">Tell us your name:</label>
                    <input type="text" class="form-control" name="name" id="name" placeholder="Zingelbert Bembledack"
                           value="${name}">
                    <span class="text-danger">${nameError}</span>
                </div>
                <div class="form-group">
                    <label for="email">Enter Your E-mail Address:</label>
                    <input type="text" class="form-control" name="email" id="email" placeholder="you@yourdomain.com"
                           value="${email}">
                    <span class="text-danger">${emailError}</span>
                    <span class="text-danger">${emailInvalidError}</span>
                </div>
                <div class="form-group">
                    <label for="password">Enter a password:</label>
                    <input type="password" class="form-control" name="password" id="password">
                    <span class="text-danger">${passwordError}</span>
                </div>
                <div class="form-group">
                    <label for="authy-countries">Country code:</label>
                    <input type="text" class="form-control" name="countryCode" id="authy-countries"
                           value="${countryCode}">
                    <span class="text-danger">${countryCodeError}</span>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone number:</label>
                    <input type="number" class="form-control" name="phoneNumber" id="phoneNumber"
                           value="${phoneNumber}">
                    <span class="text-danger">${phoneNumberError}</span>
                </div>
                <button class="btn btn-primary">Sign up</button>
            </form>
        </div>
    </layout:put>
</layout:extends>