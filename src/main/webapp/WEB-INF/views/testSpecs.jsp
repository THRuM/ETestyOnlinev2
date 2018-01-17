<%--time
  Created by IntelliJ IDEA.
  User: maciek
  Date: 16.10.17
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url value="/teacher/testSpecs/delete" var="deleteUrl" />
<spring:message code="test.time" var="timeHeader" />
<spring:message code="test.amount" var="amountHeader" />
<spring:message code="question.add.image" var="addImage" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="testParams.header" />
    </h1>

    <form:form method="post" modelAttribute="testSpecsDTO" cssClass="ui form" enctype="multipart/form-data">
        <form:errors element="div" path="*" cssClass="ui red message"/>
        <div class="row">
            <div class="two fields">
                <div class="field">
                    <h4 class="ui header">
                        ${timeHeader}
                    </h4>
                    <div class="ui divided selection list">
                        <c:forEach items="${testSpecs.times}" var="time" >
                            <div class="item">
                                <span><c:out value="${time}" /></span>
                                <a href="${deleteUrl}?time=${time}" class="ui red label" style="float: right;"><spring:message code="general.delete" /></a>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="field">
                    <h4 class="ui header">
                        ${amountHeader}
                    </h4>
                    <div class="ui divided selection list">
                        <c:forEach items="${testSpecs.amounts}" var="amount" >
                            <div class="item">
                                <span><c:out value="${amount}" /></span>
                                <a href="${deleteUrl}?amount=${amount}" class="ui red label" style="float: right;"><spring:message code="general.delete" /></a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="two fields">
                <div class="field">
                    <form:input path="valueOne" type="number" placeholder="${timeHeader}" />
                </div>

                <div class="field">
                    <form:input path="valueTwo" type="number" placeholder="${amountHeader}" />
                </div>
            </div>
        </div>

        <sec:authorize access="hasRole('ADMIN')">
        <div class="row">
            <div class="two fields">
                <div class="field">
                    <label for="file">${addImage}</label>
                    <form:input path="file" type="file" />
                </div>
            </div>
        </div>
        </sec:authorize>

        <div class="row">
            <div class="field">
                <button type="submit" class="positive ui button">
                    <spring:message code="general.add" />
                </button>
            </div>
        </div>
    </form:form>
</div>