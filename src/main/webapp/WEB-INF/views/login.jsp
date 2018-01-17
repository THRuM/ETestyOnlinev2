<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 12.09.17
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="/resources/images/logo.png" var="logoUrl" />
<spring:url value="/register" var="registerUrl" />
<spring:url value="/passwordResetRequest" var="passwordResetUrl" />
<spring:url value="/login?lang=en" var="lanEn" />
<spring:url value="/login?lang=pl" var="lanPl" />

<spring:message code="general.email" var="emailAddress" />
<spring:message code="general.password" var="password" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.2.13/dist/semantic.min.css">
    <title><spring:message code="login.title" /></title>

    <style type="text/css">
        body {
            background-color: #DADADA;
        }
        body > .grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.2.13/dist/semantic.min.js"></script>
</head>
<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                <img src="${logoUrl}" class="image">
                <div class="content">
                    <spring:message code="header.projectName" />
                </div>
            </h2>
            <c:if test="${param.error != null}">
                <div class="ui negative message">
                        <span>
                            <spring:message code="login.error" />
                        </span>
                </div>
            </c:if>
            <form:form name="f" action="login" method="post" cssClass="ui large form">
                <div class="ui stacked segment">
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input type="email" name="username" placeholder="${emailAddress}">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" name="password" placeholder="${password}">
                        </div>
                    </div>
                    <button class="ui fluid button large teal">
                        <spring:message code="login.header" />
                    </button>
                </div>
                <form:errors cssClass="ui error message" element="div" path="*" />
            </form:form>

            <div class="ui message">
                <a href="${registerUrl}">
                   <spring:message code="login.register" />
               </a>
                <div class="ui divider"></div>
                <a href="${passwordResetUrl}">
                    <spring:message code="login.reset.password" />
                </a>
                <div class="ui divider"></div>
                <span>
                    <spring:message code="login.choose.language" />
                </span>
                <a href="${lanPl}">
                    <i class="pl flag" ></i>
                </a>
                <a href="${lanEn}">
                    <i class="gb flag" ></i>
                </a>
            </div>
        </div>
    </div>
</body>
</html>