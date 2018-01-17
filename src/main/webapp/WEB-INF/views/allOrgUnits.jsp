<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 13.10.17
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/teacher/orgUnit/view" var="orgUnitViewUrl" />
<spring:url value="/teacher/orgUnit/delete" var="orgUnitDeleteUrl" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="orgUnits.header" />
    </h1>

    <table class="ui celled table">
        <thead>
        <tr>
            <th>#</th>
            <th>
                <spring:message code="general.description" />
            </th>
            <c:if test="${showOwner == true}">
                <th>
                    <spring:message code="general.user" />
                </th>
            </c:if>
            <th></th>
            <th>
                <spring:message code="orgUnits.allUsers"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orgUnits.content}" var="orgUnit" varStatus="num">
            <tr>
                <td>${(orgUnits.number * orgUnits.getSize()) + (num.index + 1)}</td>
                <td><c:out value="${orgUnit.description}" /></td>

                <c:if test="${showOwner == true}">
                    <td>
                        <c:out value="${orgUnit.owner.email}" />
                    </td>
                </c:if>

                <td>
                    <a href="${orgUnitDeleteUrl}/${orgUnit.id}" class="ui button negative">
                        <spring:message code="general.delete" />
                    </a>
                </td>
                <td>
                    <a href="${orgUnitViewUrl}/${orgUnit.id}" class="ui button positive">
                        <spring:message code="general.show" />
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${orgUnits.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${orgUnits.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${orgUnits.number == p}">
                        <a href="${allOrgUnitsUrl}?page=${p}&size=${orgUnits.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${orgUnits.number != p}">
                        <a href="${allOrgUnitsUrl}?page=${p}&size=${orgUnits.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>

            </c:forEach>
        </div>
    </c:if>

</div>