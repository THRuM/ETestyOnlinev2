<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 01.10.17
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="ui main text container">
    <h3 class="ui header">
        <spring:message code="question.main" /> ${number+1}/${maxNumber+1}
    </h3>
    <h3 class="ui header">
        <spring:message code="question.category" /> ${category}
    </h3>
    <img class="ui fluid image" id="questionImage" src="data:image/jpg;base64, <c:out value="${image}"/>">
    <form:form method="post" cssClass="ui form" name="questionForm">
        <div class="field">
            <label>
                <spring:message code="general.text" />
            </label>
            <textarea disabled style="opacity: unset" rows="5"><c:out value="${text}" /></textarea>
        </div>
        <div class="field">
            <p>
                <spring:message code="general.answers" />
            </p>
            <div class="ui divider"></div>
        </div>

        <c:forEach var="answer" items="${answers}" varStatus="number">
            <div class="two fields">
                <div class="fourteen wide field">
                    <p class="ui"><c:out value="${answer}" /></p>
                </div>
                <div class="two wide field">
                    <div class="ui slider checkbox">
                        <c:if test="${selectedAnswer == number.index}">
                            <input type="radio" id="selectedAnswer" name="selectedAnswer" value="${number.index}" checked>
                        </c:if>
                        <c:if test="${selectedAnswer != number.index}">
                            <input type="radio" id="selectedAnswer" name="selectedAnswer" value="${number.index}" >
                        </c:if>

                        <label>
                                <c:out value="${ansLetters[number.index]}" />
                        </label>
                    </div>
                </div>
            </div>
        </c:forEach>
        <button class="positive ui button" onclick="previousQuestion()">
            <spring:message code="general.previuos" />
        </button>
        <button type="submit" class="positive ui button">
            <spring:message code="general.next" />
        </button>
    </form:form>
</div>
<div style="margin-bottom: 200px" ></div>

<script>
    var timeLeft = "${secToEnd}";

    timer();

    function timer() {
        var minutes = Math.floor(timeLeft / 60);
        minutes = minutes.toString().length < 2 ? '0'+ minutes : minutes;

        var seconds = timeLeft - minutes * 60;
        seconds = seconds.toString().length < 2 ? '0'+ seconds : seconds;

        document.getElementById("timer").textContent = minutes + ":" + seconds;
        timeLeft -= 1;

        if(timeLeft < 0){
            window.clearInterval(inter);
            document.questionForm.submit();
        }
    }

    var inter = setInterval(timer, 1000);

    function previousQuestion() {
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'direction';
        input.value = 'backward';

        document.questionForm.appendChild(input);
        document.questionForm.submit();
    }
</script>


