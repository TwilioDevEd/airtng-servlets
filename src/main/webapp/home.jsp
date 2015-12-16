<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>

<layout:extends name="base">
    <layout:put block="contents" type="REPLACE">
        <div class="hero-text full-page">
            <h1>Lodging fit for a captain</h1>

            <p>The Next Generation of vacation rentals.</p>
            <a href="/properties" class="btn btn-transparent btn-lg">View Properties</a>
        </div>
    </layout:put>
</layout:extends>