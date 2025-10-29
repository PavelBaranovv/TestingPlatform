<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container container_small">
    <h1>Вход</h1>
    <form method="post">
        <div class="form-group">
            <label>Логин:
            <input type="text" name="login" required>
            </label>
        </div>

        <div class="form-group">
            <label>Пароль:
            <input type="password" name="password" required>
            </label>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn">Войти</button>
        </div>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/registration">У меня нет аккаунта, зарегистрироваться</a>
        </div>
    </form>

    <c:if test="${not empty sessionScope.login_error}">
        <div class="error-message">
            <c:out value="${sessionScope.login_error}" />
        </div>
        <c:remove var="login_error" scope="session"/>
    </c:if>
</div>
</body>
</html>