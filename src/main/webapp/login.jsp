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
    </layout:put>
</layout:extends>