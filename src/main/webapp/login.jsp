<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Вход в систему</title>
</head>
<body>
<h1>Вход в систему</h1>
<form action="login" method="post">
    <div>
        <label>
            Логин: <input type="text" name="login" required>
        </label>
    </div>

    <div>
        <label>
            Пароль: <input type="password" name="password" required>
        </label>
    </div>

    <div>
        <button type="submit">Войти</button>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/registration">У меня нет аккаунта, зарегистрироваться</a>
    </div>
</form>
<c:if test="${not empty error_message}">
    <div class="alert alert-danger">
        <c:out value="${error_message}" />
    </div>
</c:if>
</body>
</html>