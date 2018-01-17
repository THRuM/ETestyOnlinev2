<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 06.10.17
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/test/view" var="testView" />
<spring:url value="/test/begin" var="testUrl" />
<spring:url value="/test/my" var="myTestUrl" />
<spring:url var="login" value="/login" />

<div class="ui main text container">
    <div class="ui raised segment">
        <h3 class="ui header"><spring:message code="general.header.main.panel" /></h3>
    </div>
    <div class="ui raised segment">
        <h3 class="ui header"><spring:message code="general.last.tests" /></h3>

        <c:forEach items="${lastTests}" var="test">
            <table class="ui fixed single line celled table">
                <thead>
                <tr>
                    <th><spring:message code="general.categories"/></th>
                    <th><spring:message code="test.amount" /></th>
                    <th><spring:message code="test.result" /></th>
                    <th><spring:message code="general.more" /></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><c:forEach items="${test.categories}" var="category" ><c:out value="${category.description}" /> </c:forEach></td>
                    <td><c:out value="${test.numberOfQuestions}" /></td>
                    <td><c:out value="${test.result}" />%</td>
                    <td>
                        <a href="${testView}/${test.id}"><spring:message code="general.more" /></a>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:forEach>
    </div>
    <div class="ui raised segment">
        <h3 class="ui header"><spring:message code="general.quick.links" /></h3>
        <a href="${testUrl}" class="ui green button">
            <spring:message code="general.begin.test" />
        </a>
        <a class="ui green button" href="${myTestUrl}">
            <spring:message code="general.my.tests" />
        </a>
    </div>
</div>