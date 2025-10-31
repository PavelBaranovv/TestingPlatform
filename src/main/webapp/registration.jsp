<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container container-small">
    <h1>Регистрация</h1>
    <form method="post">
        <div class="form-group">
            <label>Логин:
            <input type="text" name="login" required>
            </label>
        </div>

        <c:if test="${not empty requestScope.login_violations}">
            <div class="error-message">
                <c:forEach var="violation" items="${requestScope.login_violations}">
                    ${violation}<br>
                </c:forEach>
            </div>
        </c:if>

        <div class="form-group">
            <label>Пароль:
            <input type="password" id="password" name="password" required>
            </label>
        </div>


        <c:if test="${not empty requestScope.password_violations}">
            <div class="error-message">
                <c:forEach var="violation" items="${requestScope.password_violations}">
                    ${violation}<br>
                </c:forEach>
            </div>
        </c:if>


        <div class="form-group">
            <label>Кто вы?</label>
            <div class="radio-group">
                <div class="radio-option">
                    <input type="radio" id="student" name="role" value="student" checked>
                    <label for="student">Ученик</label>
                </div>
                <div class="radio-option">
                    <input type="radio" id="teacher" name="role" value="teacher">
                    <label for="teacher">Преподаватель</label>
                </div>
            </div>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn">Зарегистрироваться</button>
        </div>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/login">У меня уже есть аккаунт</a>
        </div>
    </form>

    <c:if test="${not empty requestScope.error_message}">
        <div class="error-message">
            <c:out value="${requestScope.error_message}" />
        </div>
    </c:if>
</div>
</body>
</html>