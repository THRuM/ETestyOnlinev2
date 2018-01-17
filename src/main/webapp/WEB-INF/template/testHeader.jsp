<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 08.09.17
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/resources/images/logo.png" var="logoUrl" />
<spring:url value="/" var="indexUrl" />


<div class="ui fixed inverted menu">
    <div class="ui container">
        <a href="${indexUrl}" class="header item">
            <img class="logo" src="${logoUrl}">
            <spring:message code="header.projectName" />
        </a>
        <span class="header item" id="timer"></span>
    </div>
</div>