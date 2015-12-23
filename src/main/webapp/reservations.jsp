<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<layout:extends name="base">
    <layout:put block="hero" type="REPLACE">
        <div class="hero-text">
            <h1>Lodging fit for a captain</h1>

            <p>The Next Generation of vacation rentals.</p>
        </div>
    </layout:put>

    <layout:put block="contents" type="REPLACE">
        <div class="container">
            <div class="row">
                <core:choose>
                    <core:when test="${fn:length(reservationsAsHost) > 0}">
                        <h2> Your current reservations as a Host. </h2>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Guest Name</th>
                                <th>Phone Number</th>
                                <th>Message</th>
                            </tr>
                            </thead>
                            <tbody>
                            <core:set var="counter" value="${1}"/>
                            <core:forEach var="res" items="${reservationsAsHost}">
                                <tr>
                                    <th scope="row"> ${counter} </th>
                                    <td>${res.user.name}</td>
                                    <td>${res.anonymousPhoneNumber}</td>
                                    <td>${res.message}</td>
                                </tr>
                                <core:set var="counter" value="${counter + 1}"/>
                            </core:forEach>
                            </tbody>
                        </table>
                    </core:when>
                    <core:when test="${fn:length(reservationsAsGuest) > 0}">
                        <h2> Your current reservations as a Guest. </h2>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Guest Name</th>
                                <th>Phone Number</th>
                                <th>Message</th>
                            </tr>
                            </thead>
                            <tbody>
                            <core:set var="counter" value="${1}"/>
                            <core:forEach var="res" items="${reservationsAsGuest}">
                                <tr>
                                    <th scope="row"> ${counter} </th>
                                    <td>${res.user.name}</td>
                                    <td>${res.anonymousPhoneNumber}</td>
                                    <td>${res.message}</td>
                                </tr>
                                <core:set var="counter" value="${counter + 1}"/>
                            </core:forEach>
                            </tbody>
                        </table>
                    </core:when>
                    <core:otherwise>
                        <h2> You are not involved in any reservations. </h2>
                    </core:otherwise>
                </core:choose>
            </div>
        </div>
    </layout:put>
</layout:extends>

