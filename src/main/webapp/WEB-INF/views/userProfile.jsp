<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 03.10.17
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<spring:url value="/user/passwordChange" var="passwordChangeUrl" />
<spring:url value="/user/principal/all" var="principalChangeUrl" />
<spring:url value="/user/promote" var="promoteToTeacherUrl" />
<spring:url value="/user/me?lang=en" var="lanEn" />
<spring:url value="/user/me?lang=pl" var="lanPl" />

<div class="ui main text container">
    <h3>
        <spring:message code="header.user.profile" />
    </h3>
    <form:form method="post" cssClass="ui form">
    <table class="ui very basic collapsing celled table">
        <tbody>
            <tr>
                <td><spring:message code="general.email" /></td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td><spring:message code="general.name" /></td>
                <td>${user.name}</td>
            </tr>
            <tr>
                <td><spring:message code="general.orgUnit" /></td>
                <td>
                    ${user.orgUnit.description}
                        <sec:authorize access="@teacherServiceImpl.haveRightsToEdit(#user.id)">
                        <select name="orgUnitId" class="ui fluid dropdown">
                            <c:forEach items="${orgUnits}" var="orgUnit" >
                                <c:if test="${orgUnit.id == user.orgUnit.id}">
                                    <option selected value="${orgUnit.id}">${orgUnit.description}</option>
                                </c:if>
                                <c:if test="${orgUnit.id != user.orgUnit.id}">
                                    <option value="${orgUnit.id}">${orgUnit.description}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </sec:authorize>
                </td>
            </tr>
            <tr>
                <td><spring:message code="general.account.enabled" /></td>
                <td>
                    <c:choose>
                        <c:when test="${user.enabled == true}">
                            <spring:message code="general.yes" />
                        </c:when>
                        <c:otherwise>
                            <spring:message code="general.no" />
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><spring:message code="general.roles" /></td>
                <td>${user.effectiveRole}</td>
            </tr>
            <tr>
                <td><spring:message code="general.principal" /></td>
                <td>${user.principal.email}</td>
            </tr>
            <sec:authorize access="principal.username.equals(#user.email)">
                <tr>
                    <td><spring:message code="login.choose.language" /></td>
                    <td>
                        <a href="${lanPl}">
                            <i class="pl flag" ></i>
                        </a>
                        <a href="${lanEn}">
                            <i class="gb flag" ></i>
                        </a>
                    </td>
                </tr>
            </sec:authorize>
        </tbody>
    </table>

    <div class="ui message">
        <sec:authorize access="principal.username.equals(#user.email)">
            <a href="${passwordChangeUrl}" class="positive ui button">
                <spring:message code="password.change.header" />
            </a>
            <sec:authorize access="!#user.roles.contains(T(etestyonline.model.util.SETTINGS).ADMIN)">
                <a href="${principalChangeUrl}" class="positive ui button">
                    <spring:message code="principal.change" />
                </a>
            </sec:authorize>
            <sec:authorize access="!#user.roles.contains(T(etestyonline.model.util.SETTINGS).TEACHER)">
                <a href="${promoteToTeacherUrl}" class="positive ui button">
                    <spring:message code="general.claim.promote" />
                </a>
            </sec:authorize>
        </sec:authorize>

        <sec:authorize access="@teacherServiceImpl.haveRightsToEdit(#user.id)">
            <button type="submit" class="positive ui button">
                <spring:message code="general.save" />
            </button>
        </sec:authorize>
    </div>

    </form:form>
</div>



