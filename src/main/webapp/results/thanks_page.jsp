<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Результаты теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="small_container">
    <h1>Спасибо!</h1>
    <h3>Ваши ответы на тест <c:out value="${requestScope.test.name}"/> записаны</h3>

    <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'STUDENT'}">
        <form method="post" class="btn-group">
            <button name="choice" value="home" class="btn">На главную</button>
        </form>
    </c:if>
</div>
</body>
</html>