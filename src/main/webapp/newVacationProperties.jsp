<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:extends name="base">
    <layout:put block="contents" type="REPLACE">
        <div class="container">
            <h1>New Vacation Property</h1>

            <form action="/properties-new" method="post" class="form">
                <div class="form-group">
                    <label for="description">Description:</label>
                    <input type="text" class="form-control" name="description" id="description"
                           placeholder="Description"
                           value="${description}">
                    <span class="text-danger">${descriptionError}</span>
                </div>
                <div class="form-group">
                    <label for="imageUrl">Image Url:</label>
                    <input type="text" class="form-control" name="imageUrl" id="imageUrl"
                           placeholder="http://domain.com/image.png"
                           value="${imageUrl}">
                    <span class="text-danger">${imageUrlError}</span>
                </div>

                <div class="actions">
                    <input type="submit" value="Create Property" class="btn btn-lg btn-primary"/>
                </div>
            </form>

            <a href="/properties">Back</a>
        </div>
    </layout:put>
</layout:extends>

