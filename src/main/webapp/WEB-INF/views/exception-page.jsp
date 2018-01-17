<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 15.09.17
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/resources/images/warning.png" var="warningUrl" />

<div class="ui main text container">
    <div class="ui centered grid">
        <div class="twelve wide column">
            <img class="ui fluid image" id="questionImage" src="${warningUrl}">

            <div class="ui negative message">
                <h1 class="ui header">
                    <spring:message code="error.header" />
                </h1>
                <p><c:out value="${exception.message}" /></p>
            </div>
        </div>
    </div>
</div>
