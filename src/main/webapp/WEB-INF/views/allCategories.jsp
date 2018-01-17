<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 11.10.17
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/category/delete" var="deleteUrl" />
<spring:url value="/category/add" var="addCategoryUrl" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="category.all.header" />
    </h1>
    <table class="ui celled table">
        <thead>
        <tr>
            <th>
                <spring:message code="general.description" />
            </th>
            <th>
                <spring:message code="general.delete" />
            </th>
        </tr></thead>
        <tbody>
        <c:forEach items="${categories.content}" var="category">
            <tr>
                <td><c:out value="${category.description}" /></td>
                <td>
                    <a href="${deleteUrl}/${category.id}" class="negative ui button">
                        <spring:message code="general.delete" />
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${categories.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${categories.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${categories.number == p}">
                        <a href="${allCategoriesUrl}?page=${p}&size=${categories.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${categories.number != p}">
                        <a href="${allCategoriesUrl}?page=${p}&size=${categories.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>
    <a href="${addCategoryUrl}" type="button" class="ui button positive">
        <spring:message code="category.header" />
    </a>
</div>