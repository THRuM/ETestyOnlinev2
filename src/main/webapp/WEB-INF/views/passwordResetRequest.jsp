<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 15.09.17
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="general.email" var="email"/>

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="reset.header" />
    </h1>
    <form:form method="post" modelAttribute="email" cssClass="ui form">
        <form:errors element="div" path="*" cssClass="ui red message"/>
        <div class="field">
            <label>
                <spring:message code="general.email" />
            </label>
            <input type="email" name="email" placeholder="${email}">
        </div>
        <button type="submit" class="positive ui button">
            <spring:message code="reset" />
        </button>
    </form:form>
</div>



