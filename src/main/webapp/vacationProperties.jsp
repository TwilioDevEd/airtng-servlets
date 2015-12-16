<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="base">
    <layout:put block="contents" type="REPLACE">
        <div class="container">
            <div class="row">
                <core:forEach var="prop" items="${vacationProperties}">
                    <div class="col-md-4">
                        <a href="/" class="property">
                            <img src="${prop.imageUrl}" alt="Vacation Property"/>

                            <h2>${prop.description}"</h2>
                        </a>
                    </div>
                </core:forEach>

            </div>
        </div>
    </layout:put>
</layout:extends>

