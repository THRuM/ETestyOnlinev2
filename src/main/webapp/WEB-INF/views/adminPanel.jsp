<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 11.10.17
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/admin/user/all" var="allUsersUrl" />
<spring:url value="/admin/orgUnit/all" var="allOrgUnitUrl" />
<spring:url value="/teacher/orgUnit/add" var="addOrgUnitsUrl" />
<spring:url value="/admin/claims" var="claimsUrl" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="teacher.panel.header" />
    </h1>

    <div class="ui grid">
        <div class="eight wide column">
            <div class="ui segments">
                <div class="ui segment">
                    <p><spring:message code="header.teacher.users"/></p>
                </div>
                <div class="ui secondary segment">
                    <a href="${allUsersUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="teacher.all.users" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${claimsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="admin.claims.header" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${allOrgUnitUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="orgUnits.header" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${addOrgUnitsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="orgUnits.add.header" />
                    </a>
                </div>
            </div>
        </div>
        <div class="eight wide column">

        </div>
    </div>
</div>