<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 08.09.17
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/question/delete/" var="delete" />

<div class="ui main text container">
    <h1 class="ui header">
        <spring:message code="question.header" />
    </h1>
    <img class="ui fluid image" id="questionImage" src="data:image/jpg;base64, ${question.imgStr}">
    <form:form method="post" modelAttribute="question" cssClass="ui form" enctype="multipart/form-data" >
        <form:errors element="div" path="correctAnswer" cssClass="ui red message"/>
        <div class="field">
            <label>
                <spring:message code="question.text" />
            </label>
            <form:textarea path="text" id="text" />
            <form:errors element="div" path="text" cssClass="ui red message"/>
        </div>
        <div class="field">
            <label>
                <spring:message code="question.category" />
            </label>
            <form:select path="category" items="${categories}" itemLabel="description" itemValue="id" cssClass="ui dropdown"/>
            <form:errors element="div" path="category" cssClass="ui red message"/>
        </div>
        <div class="two fields">
            <div class="twelve wide field">
                <label>
                    <spring:message code="question.answer1" />
                </label>
                <form:input path="answer1" id="answer1" />
                <form:errors element="div" path="answer1" cssClass="ui red message"/>
            </div>
            <div class="four wide field" style="margin-top: 2.3em">
                <div class="ui slider checkbox">
                    <form:radiobutton path="correctAnswer" id="1" value="0"/>
                    <label>
                        <spring:message code="general.correct" />
                    </label>
                </div>
            </div>
        </div>
        <div class="two fields">
            <div class="twelve wide field">
                <label>
                    <spring:message code="question.answer2" />
                </label>
                <form:input path="answer2" id="answer2" />
                <form:errors element="div" path="answer2" cssClass="ui red message"/>
            </div>
            <div class="four wide field" style="margin-top: 2.3em">
                <div class="ui slider checkbox">
                    <form:radiobutton path="correctAnswer" id="2" value="1" />
                    <label>
                        <spring:message code="general.correct" />
                    </label>
                </div>
            </div>
        </div>
        <div class="two fields">
            <div class="twelve wide field">
                <label>
                    <spring:message code="question.answer3" />
                </label>
                <form:input path="answer3" id="answer3" />
                <form:errors element="div" path="answer3" cssClass="ui red message"/>
            </div>
            <div class="four wide field" style="margin-top: 2.3em">
                <div class="ui slider checkbox">
                    <form:radiobutton path="correctAnswer" id="3" value="2" />
                    <label>
                        <spring:message code="general.correct" />
                    </label>
                </div>
            </div>
        </div>
        <div class="two fields">
            <div class="twelve wide field">
                <label>
                    <spring:message code="question.answer4" />
                </label>
                <form:input path="answer4" id="answer4" />
                <form:errors element="div" path="answer4" cssClass="ui red message"/>
            </div>
            <div class="four wide field" style="margin-top: 2.3em">
                <div class="ui slider checkbox">
                    <form:radiobutton path="correctAnswer" id="4" value="3"/>
                    <label>
                        <spring:message code="general.correct" />
                    </label>
                </div>
            </div>
        </div>

        <div class="two fields">
            <div class="twelve wide field">
                <label for="file"><spring:message code="question.add.image" /></label>
                <input id="file" type="file" name="file" />
            </div>
            <c:if test="${addQuestion == false}" >
                <div class="four wide field" style="margin-top: 2.3em">
                    <div class="ui slider checkbox">
                        <form:checkbox path="resetImage" />
                        <label>
                            <spring:message code="question.rem.image" />
                        </label>
                    </div>
                </div>
            </c:if>
        </div>

        <div>
            <c:if test="${addQuestion == true}" >
                <button type="submit" class="positive ui button">
                    <spring:message code="general.add" />
                </button>
            </c:if>
            <c:if test="${addQuestion == false}" >
                <button type="submit" class="positive ui button">
                    <spring:message code="general.save" />
                </button>

                <a href="${delete}${question.questionId}" type="button" class="negative ui button">
                    <spring:message code="general.delete" />
                </a>
            </c:if>
        </div>
    </form:form>
</div>

<script>
    $('.ui.checkbox')
        .checkbox()
    ;
</script>