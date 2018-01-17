<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 01.10.17
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/test/question" var="testQuestion" />
<spring:url value="/test/end" var="testEnd" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="test.rewiew" />
    </h1>

    <table class="ui celled table">
        <thead>
        <tr>
            <th>#</th>
            <th>
                <spring:message code="general.text" />
            </th>
            <th>
                <spring:message code="general.answer" />
            </th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${questions}" var="question" varStatus="num">
            <tr>
                <td>${num.index + 1}</td>
                <td><c:out value="${question.text}" /></td>
                <td>
                    <c:if test="${question.selectedAnswer != 99}">
                        <c:out value="${ansLetters[question.selectedAnswer]}" />
                    </c:if>
                    <c:if test="${question.selectedAnswer == 99}">
                        <spring:message code="general.none" />
                    </c:if>
                </td>

                <td>
                    <a href="${testQuestion}/${num.index}" type="button" class="ui button">
                        <spring:message code="general.change" />
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="${testEnd}" type="button" class="green ui button">
        <spring:message code="test.end" />
    </a>
</div>

