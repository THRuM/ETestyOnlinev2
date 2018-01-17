<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 26.09.17
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/teacher/claims/accept" var="accept" />
<spring:url value="/teacher/claims/reject" var="reject" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="teacher.claims" />
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
            <th></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${tokens.content}" var="token" varStatus="num">
                <tr>
                    <td>${(tokens.number * tokens.getSize()) + (num.index + 1)}</td>
                    <td><c:out value="${token.user.name}" /></td>
                    <td><c:out value="${token.user.email}" /></td>
                    <td>
                        <a href="${accept}/${token.id}" class="positive ui button">
                            <spring:message code="general.accept" />
                        </a>
                    </td>
                    <td>
                        <a href="${reject}/${token.id}" class="negative ui button">
                            <spring:message code="general.reject" />
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${tokens.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${tokens.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${tokens.number == p}">
                        <a href="${allTeacherClaimsUrl}?page=${p}&size=${tokens.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${tokens.number != p}">
                        <a href="${allTeacherClaimsUrl}?page=${p}&size=${tokens.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>
</div>
