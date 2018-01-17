<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 25.09.17
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/user/principal" var="principal" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="principal.header" />
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
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${teachers.content}" var="teacher" varStatus="num">
            <tr>
                <td>${(teachers.number * teachers.getSize()) + (num.index + 1)}</td>
                <td><c:out value="${teacher.name}" /></td>
                <td><c:out value="${teacher.email}" /></td>
                <td>
                    <a href="${principal}/${teacher.id}" class="positive ui button">
                        <spring:message code="general.send.claim" />
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${teachers.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${teachers.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${teachers.number == p}">
                        <a href="${allPrincipalsUrl}?page=${p}&size=${teachers.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${teachers.number != p}">
                        <a href="${allPrincipalsUrl}?page=${p}&size=${teachers.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>
</div>