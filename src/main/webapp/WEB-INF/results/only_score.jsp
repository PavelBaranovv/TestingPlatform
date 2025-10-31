<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Результаты теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="container container-small">

    <h1>
        Тест <c:out value="${requestScope.test.name}"/>
        <c:choose>
            <c:when test="${requestScope.attempt.success}">пройден успешно!</c:when>
            <c:otherwise>не пройден!</c:otherwise>
        </c:choose>
    </h1>

    <h3>Верных ответов: ${requestScope.attempt.score} из ${requestScope.test.questionCount}</h3>

    <c:if test="${requestScope.attempt.success == false}">
        <div class="info-block">
            <p>Для успешного прохождения необходимо верно ответить хотя бы на ${requestScope.test.needToAnswer}</p>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'STUDENT'}">
        <form method="post" class="btn-group">
            <button name="choice" value="home" class="btn">На главную</button>
        </form>
    </c:if>
</div>
</body>
</html>