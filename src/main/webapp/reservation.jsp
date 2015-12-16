<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
taglib prefix="layout" uri=""%>

<layout:extends name="base">
    <layout:put block="contents" type="REPLACE">
        <div class="property-detail">
            <div class="overview">
                <img src="${vacationProperty.imageUrl}" alt="Vacation property image"/>
            </div>
            <div class="container">
                <h1>${vacationProperty.description}</h1><span></span>
                <hr>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Make a Reservation</h3>
                    </div>
                    <div class="panel-body">
                        <form action="/reservation" method="post">
                            <input type="hidden" name="propertyId" id="propertyId" value="${vacationProperty.id}"/>

                            <div class="form-group">
                                <label for="message">Would you like to say anything to the host?</label>
                                <textarea class="form-control" name="message" id="message"
                                          size="10x10" rows="2" cols="20"
                                          placeholder="Hello! I am hoping to stay in your intergalactic suite..."
                                          value="${message}"></textarea>
                                <span class="text-danger">${messageError}</span>
                            </div>
                            <button class="btn btn-primary">Reserve Now</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </layout:put>
</layout:extends>

