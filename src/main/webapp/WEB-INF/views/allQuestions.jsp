<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 08.09.17
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/question/all" var="allQuestionUrl" />

<spring:url value="/question/edit" var="questionEdit" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="question.all" />
    </h1>

    <table class="ui celled table">
        <thead>
            <tr>
                <th>#</th>
                <th>
                    <spring:message code="question.text" />
                </th>
                <th>
                    <spring:message code="question.category" />
                </th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${questions.content}" var="question" varStatus="num">
                <tr>
                    <td>${(questions.number * questions.getSize()) + (num.index + 1)}</td>
                    <td><c:out value="${question.text}" /></td>
                    <td><c:out value="${question.category.description}" /></td>
                    <td>
                        <a href="${questionEdit}/${question.id}" class="ui button">
                            <spring:message code="general.more" />
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${questions.getTotalPages() > 1}">
        <div class="ui pagination menu">
            <c:forEach var = "p" begin = "0" end = "${questions.getTotalPages() - 1}">

                <c:choose>
                    <c:when test="${questions.number == p}">
                        <a href="${allQuestionUrl}?page=${p}&size=${questions.getSize()}" class="item active">
                                ${p+1}
                        </a>
                    </c:when>
                    <c:when test="${questions.number != p}">
                        <a href="${allQuestionUrl}?page=${p}&size=${questions.getSize()}" class="item">
                                ${p+1}
                        </a>
                    </c:when>
                </c:choose>


            </c:forEach>
        </div>
    </c:if>
</div>