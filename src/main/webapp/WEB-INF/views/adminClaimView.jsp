<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 04.10.17
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/admin/claims/accept" var="acceptClaimUrl" />
<spring:url value="/admin/claims/reject" var="rejectClaimUrl" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="admin.claims.single.header" />
    </h1>

    <h3>
        <spring:message code="general.claim.id" /> ${claim.id}
    </h3>
    <h4>
        <spring:message code="general.user" /> <c:out value="${claim.user.email}" />
    </h4>

    <h5>
        <spring:message code="general.reason" />
    </h5>
    <p><c:out value="${claim.description}" /></p>

    <a href="${acceptClaimUrl}/${claim.id}" class="positive ui button">
        <spring:message code="general.accept" />
    </a>
    <a href="${rejectClaimUrl}/${claim.id}" class="negative ui button">
        <spring:message code="general.reject" />
    </a>
</div>