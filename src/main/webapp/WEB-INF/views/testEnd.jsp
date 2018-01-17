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

<spring:url value="/" var="indexUrl" />

<div class="ui main text container">
    <div class="ui raised segment">
        <h3 class="ui header"><spring:message code="test.result" /></h3>

            <table class="ui fixed single line celled table">
                <tbody>
                    <tr>
                        <td><spring:message code="test.result.message" /></td>
                        <td><b><c:out value="${result}" />%</b></td>
                    </tr>
                </tbody>
            </table>
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