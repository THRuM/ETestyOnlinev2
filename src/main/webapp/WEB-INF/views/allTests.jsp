<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 02.10.17
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/test/view" var="testView" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="teacher.panel.header" />
    </h1>

    <table class="ui celled table">
        <thead>
        <tr>
            <th>#</th>
            <c:if test="${showUser == true}">
                <th>
                    <spring:message code="general.user" />
                </th>
            </c:if>
            <th>
                <spring:message code="general.data" />
            </th>
            <th>
                <spring:message code="general.categories" />
            </th>
            <th>
                <spring:message code="general.result" />
            </th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tests.content}" var="test" varStatus="num">
            <tr>
                <td>${(tests.number * tests.getSize()) + (num.index + 1)}</td>
                <c:if test="${showUser == true}">
                    <td><c:out value="${test.user.email}" /></td>
                </c:if>
                <td><c:out value="${test.dateISOFormatted}" /></td>
                <td><c:out value="${test.categoriesAsString}" /></td>
                <td><c:out value="${test.result}" /></td>
                <td>
                    <a href="${testView}/${test.id}"><spring:message code="general.more" /></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${tests.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${tests.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${tests.number == p}">
                        <a href="${allTestsUrl}?page=${p}&size=${tests.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${tests.number != p}">
                        <a href="${allTestsUrl}?page=${p}&size=${tests.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>
</div>