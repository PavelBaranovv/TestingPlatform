<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Добро пожаловать!</h1>

    <form method="post" class="form-group">
        <div class="form-group">
            <label>
            <input type="text" name="test_id" placeholder="ID теста" required>
            </label>
        </div>

        <button type="submit" class="btn" name="choice" value="go_to_test">Перейти к тесту</button>
    </form>

    <c:if test="${not empty sessionScope.id_error}">
        <div class="error-message">
            <c:out value="${sessionScope.id_error}" />
        </div>
        <c:remove var="id_error" scope="session"/>
    </c:if>

    <form>
        <div class="btn-group">
            <button type="submit" name="choice" value="completed_tests" class="btn btn-secondary">Пройденные тесты</button>
        </div>
    </form>

</div>
</body>
</html>