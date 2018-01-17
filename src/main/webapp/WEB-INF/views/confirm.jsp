<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 11.10.17
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="ui main text container">
    <h1 class="ui header">
        <c:out value="${header_message}" />
    </h1>
    <h5>
        <c:out value="${message}" />
    </h5>
</div>