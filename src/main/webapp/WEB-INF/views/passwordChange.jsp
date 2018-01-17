<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 06.10.17
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="general.password" var="password" />
<spring:message code="general.password.old" var="oldPassword" />
<spring:message code="general.password.repeat" var="repPassword" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="password.change.header" />
    </h1>

    <form:form modelAttribute="changePassword" method="post" cssClass="ui form">
        <form:errors element="div" path="*" cssClass="ui red message"/>
        <div class="field">
            <label>
                <spring:message code="general.password.old" />
            </label>
            <input type="password" name="oldPassword" placeholder="${oldPassword}">
        </div>
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
        </div>

        <button type="submit" class="positive ui button">
            <spring:message code="general.change" />
        </button>
    </form:form>

</div>