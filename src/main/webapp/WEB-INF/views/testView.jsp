<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 02.10.17
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/test/user" var="allUserTestsUrl" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="header.test" />
    </h1>

    <div class="ui grid">
        <div class="sixteen wide column">
            <div class="row">
                <div class="ui segment">
                    <div class="ui labeled button" tabindex="0">
                        <div class="ui button">
                            <i class="user icon"></i>
                        </div>
                        <a href="${allUserTestsUrl}/${testFn.user.id}" class="ui basic label">
                            <c:out value="${testFn.user.email}" />
                        </a>
                    </div>
                    <div class="ui labeled button" tabindex="0">
                        <div class="ui button">
                            <i class="calendar icon"></i>
                        </div>
                        <p class="ui basic label">
                            <c:out value="${testFn.dateISOFormatted}" />
                        </p>
                    </div>
                    <div class="ui labeled button" tabindex="0">
                        <div class="ui button">
                            <i class="star icon"></i>
                        </div>
                        <p class="ui basic label">
                            <c:out value="${testFn.result}" />%
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="ui segment">
                    <div class="ui labeled button" tabindex="0">
                        <div class="ui button">
                            <i class="clone icon"></i>
                        </div>
                        <a class="ui basic label">
                            <c:out value="${testFn.categoriesAsString}" />
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="ui top attached tabular menu">
        <c:forEach items="${testFn.questions}" var="navQuestion" varStatus="num">
            <c:if test="${(num.index + 1) == 1}">
                <a class="item active" data-tab="${num.index + 1}">${num.index + 1}</a>
            </c:if>
            <c:if test="${(num.index + 1) != 1}">
                <a class="item" data-tab="${num.index + 1}">${num.index + 1}</a>
            </c:if>
        </c:forEach>
    </div>

    <c:forEach items="${testFn.questions}" var="question" varStatus="num">
        <c:if test="${(num.index + 1) == 1}">
            <div class="ui bottom attached tab segment active" data-tab="${num.index + 1}">
        </c:if>
        <c:if test="${(num.index + 1) != 1}" >
            <div class="ui bottom attached tab segment" data-tab="${num.index + 1}">
        </c:if>
                <img class="ui fluid image" id="questionImage" src="data:image/jpg;base64, ${question.imgStr}">
                <h5><spring:message code="question.category" />: ${question.category.description}</h5>
                <h4><c:out value="${question.text}" /></h4>

                <c:if test="${question.selectedAnswer == 99}">
                    <p class="ui red header"><spring:message code="general.not.selected.answer" /></p>
                </c:if>

                <c:forEach var="answer" items="${question.answers}" varStatus="letter">

                    <c:choose>
                        <c:when test="${answer.equals(question.correctAnswer)}">
                            <p class="ui green header"><c:out value="${answer}" /></p>
                        </c:when>
                        <c:when test="${answer.equals(question.answers[question.selectedAnswer])}">
                            <p class="ui red header"><c:out value="${answer}" /></p>
                        </c:when>
                        <c:otherwise>
                            <p class="ui header"><c:out value="${answer}" /></p>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </div>
    </c:forEach>

</div>

<script>
    $('.menu .item')
        .tab()
    ;
</script>
