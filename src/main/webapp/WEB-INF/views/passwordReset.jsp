<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 15.09.17
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<spring:message code="general.password" var="password" />
<spring:message code="general.password.repeat" var="repPassword" />


<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="reset.header" />
    </h1>

    <c:if test="${isValid}">
        <c:if test="${error != null}">
            <div class="ui warning message">
                    ${error}
            </div>
        </c:if>
        <form:form modelAttribute="changePassword" method="post" class="ui form">
        <form:errors element="div" path="*" cssClass="ui red message"/>
            <div class="field">
                <label>
                    <spring:message code="general.password" />
                </label>
                <input type="password" name="password" placeholder="${password}">
            </div>
            <div class="field">
                <label>
                    <spring:message code="general.password.repeat" />
                </label>
                <input type="password" name="matchingPassword" placeholder="${repPassword}">
                <input type="hidden" name="oldPassword" value="oldPassword">
            </div>
            <button type="submit" class="positive ui button">
                <spring:message code="reset" />
            </button>
        </form:form>
    </c:if>

    <c:if test="${!isValid}">
        <h3>
            <spring:message code="reset.token.not.found" />
        </h3>
    </c:if>

</div>

