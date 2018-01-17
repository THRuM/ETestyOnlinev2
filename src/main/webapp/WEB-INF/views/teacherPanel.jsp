<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 13.10.17
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/teacher/users/all" var="allUsersUrl" />
<spring:url value="/teacher/claims" var="claimsUrl" />
<spring:url value="/teacher/orgUnit/all" var="allOrgUnitsUrl" />
<spring:url value="/teacher/orgUnit/add" var="addOrgUnitsUrl" />
<spring:url value="/question/all" var="allQuestionsUrl" />
<spring:url value="/question/add" var="addQuestionsUrl" />
<spring:url value="/category/add" var="addCategoryUrl" />
<spring:url value="/category/all" var="allCategoryUrl" />
<spring:url value="/teacher/testSpecs" var="editTestSpecsUrl" />
<spring:url value="/teacher/test/all" var="allTestUrl" />

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
                        <spring:message code="header.teacher.claims" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${allOrgUnitsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="orgUnits.header" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${addOrgUnitsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="orgUnits.add.header" />
                    </a>
                </div>
            </div>
            <div class="ui segments">
                <div class="ui segment">
                    <p><spring:message code="general.test"/></p>
                </div>
                <div class="ui secondary segment">
                    <a href="${allTestUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="header.teacher.tests" />
                    </a>
                </div>
            </div>
        </div>
        <div class="eight wide column">
            <div class="ui segments">
                <div class="ui segment">
                    <p>
                        <spring:message code="general.questions" />
                    </p>
                </div>
                <div class="ui secondary segment">
                    <a href="${allQuestionsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="header.teacher.questions" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${addQuestionsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="question.header" />
                    </a>
                </div>
            </div>
            <div class="ui segments">
                <div class="ui segment">
                    <p>
                        <spring:message code="general.categories" />
                    </p>
                </div>
                <div class="ui secondary segment">
                    <a href="${allCategoryUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="category.all.header" />
                    </a>
                </div>
                <div class="ui secondary segment">
                    <a href="${addCategoryUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="category.header" />
                    </a>
                </div>
            </div>
            <div class="ui segments">
                <div class="ui segment">
                    <p>
                        <spring:message code="testParams.header" />
                    </p>
                </div>
                <div class="ui secondary segment">
                    <a href="${editTestSpecsUrl}" type="button" class="ui button positive fluid">
                        <spring:message code="testParams.edit" />
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>