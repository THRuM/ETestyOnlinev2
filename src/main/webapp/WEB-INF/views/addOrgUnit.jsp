<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 13.10.17
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="orgUnits.add.header" />
    </h1>
    <form:form method="post" modelAttribute="descriptionObj" cssClass="ui form">
        <form:errors element="div" path="description" cssClass="ui red message"/>
        <div class="field">
            <label>
                <spring:message code="general.description" />
            </label>
            <form:input path="description" />
        </div>
        <button type="submit" class="positive ui button">
            <spring:message code="general.add" />
        </button>
    </form:form>
</div>