<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 04.10.17
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/admin/claims/view" var="claimViewUrl" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="admin.claim.header" />
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
        <c:forEach items="${tokens.content}" var="token" varStatus="num">
            <tr>
                <td>${(tokens.number * tokens.getSize()) + (num.index + 1)}</td>
                <td><c:out value="${token.user.name}" /></td>
                <td><c:out value="${token.user.email}" /></td>
                <td>
                    <a href="${claimViewUrl}/${token.id}">
                        <spring:message code="general.more" />
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
                        <a href="${allAdminClaimsUrl}?page=${p}&size=${tokens.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${tokens.number != p}">
                        <a href="${allAdminClaimsUrl}?page=${p}&size=${tokens.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>
</div>

