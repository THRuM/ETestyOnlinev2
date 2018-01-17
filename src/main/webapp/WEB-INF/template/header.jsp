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
<spring:url value="/test/begin" var="testUrl" />
<spring:url value="/test/my" var="myTestUrl" />
<spring:url value="/user/me" var="profileUrl" />
<spring:url value="/question/all" var="allQuestionsUrl" />
<spring:url value="/question/add" var="addQuestionUrl" />
<spring:url value="/teacher/claims" var="claimsUrl" />
<spring:url value="/teacher/users/all" var="allUsersUrl" />
<spring:url value="/category/add" var="addCategoryUrl" />
<spring:url value="/category/all" var="allCategoryUrl" />
<spring:url value="/admin" var="adminPanelUrl" />
<spring:url value="/teacher" var="teacherPanelUrl" />
<spring:url value="/teacher/test/all" var="allTeacherTestsUrl" />
<spring:url value="/logout" var="logoutUrl" />


<div class="ui fixed inverted menu">
    <div class="ui container">
        <a href="${indexUrl}" class="header item">
            <img class="logo" src="${logoUrl}">
            <spring:message code="header.projectName" />
        </a>
        <sec:authorize access="isAuthenticated()">
            <a href="${indexUrl}" class="item">
                <spring:message code="header.home" />
            </a>
            <a href="${testUrl}" class="item">
                <spring:message code="header.test" />
            </a>
            <div class="ui simple dropdown item">
                <spring:message code="general.more" />
                <i class="dropdown icon"></i>
                <div class="menu">
                    <div class="header">
                        <spring:message code="header.user" />
                    </div>
                    <a class="item" href="${myTestUrl}">
                        <spring:message code="header.user.tests" />
                    </a>
                    <a class="item" href="${profileUrl}">
                        <spring:message code="header.user.profile" />
                    </a>

                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <div class="divider"></div>
                        <div class="header">
                            <spring:message code="header.teacher" />
                        </div>

                        <a class="item" href="${teacherPanelUrl}">
                            <spring:message code="header.teacher.panel" />
                        </a>
                        <a class="item" href="${allTeacherTestsUrl}">
                            <spring:message code="header.teacher.tests" />
                        </a>
                        <a class="item" href="${allQuestionsUrl}">
                            <spring:message code="header.teacher.questions" />
                        </a>
                        <a class="item" href="${claimsUrl}">
                            <spring:message code="header.teacher.claims" />
                        </a>
                        <a class="item" href="${allUsersUrl}">
                            <spring:message code="header.teacher.users" />
                        </a>
                        <a class="item" href="${addQuestionUrl}">
                            <spring:message code="question.header" />
                        </a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="divider"></div>
                        <div class="header">
                            <spring:message code="general.admin" />
                        </div>

                        <a class="item" href="${adminPanelUrl}">
                            <spring:message code="header.admin.panel" />
                        </a>
                    </sec:authorize>
                    <sec:authorize url="/logout">
                        <div class="divider"></div>
                        <a class="item" href="${logoutUrl}">
                            <spring:message code="general.logout" />
                        </a>
                    </sec:authorize>
                </div>
            </div>
        </sec:authorize>

    </div>
</div>