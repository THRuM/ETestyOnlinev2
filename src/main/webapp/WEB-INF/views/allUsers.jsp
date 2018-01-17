<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 03.10.17
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/teacher/users/view" var="userView" />
<spring:url value="/test/user" var="userTestView" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="teacher.all.users" />
    </h1>

    <table class="ui celled table">
        <thead>
        <tr>
            <th>#</th>
            <th>
                <spring:message code="general.name" />
            </th>
            <th>
                <spring:message code="general.email" />
            </th>
            <th>
                <spring:message code="general.orgUnit" />
            </th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${users.content}" var="user" varStatus="num">
            <tr>
                <td>${(users.number * users.getSize()) + (num.index + 1)}</td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.orgUnit.description}" /></td>
                <td>
                    <a href="${userTestView}/${user.id}">
                        <spring:message code="header.user.tests" />
                    </a>
                </td>
                <td>
                    <a href="${userView}/${user.id}">
                        <spring:message code="header.user.profile"/>
                    </a>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${users.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${users.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${users.number == p}">
                        <a href="${allUsersUrl}?page=${p}&size=${users.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${users.number != p}">
                        <a href="${allUsersUrl}?page=${p}&size=${users.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>
</div>



