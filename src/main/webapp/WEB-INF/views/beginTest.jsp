<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 29.09.17
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="test.params" />
    </h1>
    <form:form method="post" modelAttribute="testParams" cssClass="ui form">
        <form:errors element="div" path="*" cssClass="ui red message"/>
        <div class="two fields">
            <div class="field">
                <label>
                    <spring:message code="test.time" />
                </label>
                <form:select path="time" items="${times}" cssClass="ui fluid dropdown"/>
            </div>

            <div class="field">
                <label>
                    <spring:message code="test.amount" />
                </label>
                <form:select path="amount" items="${amounts}" cssClass="ui fluid dropdown"/>
            </div>
        </div>
        <div class="field">
            <label>
                <spring:message code="test.category" />
            </label>
            <div class="ui dropdown selection multiple" tabindex="0">
                <form:select path="categoryIds" multiple="true" cssClass="ui dropdown">
                    <form:options items="${categories}" itemLabel="description" itemValue="id"/>
                </form:select>
                <i class="dropdown icon"></i>
                <div class="default text">
                    <spring:message code="general.choose" />
                </div>

                <div class="menu" tabindex="-1">
                    <c:forEach items="${categories}" var="category" >
                        <div class="item" data-value="${category.id}">${category.description}</div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <button type="submit" class="positive ui button">
            <spring:message code="general.start" />
        </button>
    </form:form>
</div>

<script>
    $('.ui.dropdown')
        .dropdown()
    ;
</script>