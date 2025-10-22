<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация</h1>
<form action="registration" method="post">
    <div>
        <label>
            Логин: <input type="text" name="login" required>
        </label>
    </div>

    <div>
        <label>
            Пароль: <input type="password" id="password" name="password" required>
        </label>
    </div>

    <div>
        <label>Тип аккаунта:</label><br>
        <label>
            <input type="radio" id="student" name="role" value="student" checked> Ученик
        </label><br>

        <label>
            <input type="radio" id="teacher" name="role" value="teacher"> Преподаватель
        </label>
    </div>

    <div>
        <button type="submit">Зарегистрироваться</button>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/login">У меня уже есть аккаунт</a>
    </div>
</form>
<c:if test="${not empty error_message}">
    <div class="alert alert-danger">
        <c:out value="${error_message}" />
    </div>
</c:if>
</body>
</html>