<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Тест создан</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="container">

    <h1>Тест <c:out value="${sessionScope.creating_test.name}"/> создан!</h1>

    <div class="info">
        <p><strong>ID теста:</strong> </p>
        <div class="id-block">
            ${sessionScope.creating_test.id}
        </div>
        <p>Отправьте его тем, кого хотите проверить</p>
    </div>

    <div class="info-block">
        <p>Вопросов в тесте: ${sessionScope.creating_test.questionCount}</p>
        <p>Необходимо ответить на ${sessionScope.creating_test.needToAnswer}</p>
        <p>Отображение результатов:
            <c:choose>
                <c:when test="${sessionScope.creating_test.showResult == 'MISTAKES_AND_CORRECTS'}">
                    Ошибки и верные ответы
                </c:when>
                <c:when test="${sessionScope.creating_test.showResult == 'ONLY_MISTAKES'}">
                    Только ошибки
                </c:when>
                <c:when test="${sessionScope.creating_test.showResult == 'ONLY_SCORE'}">
                    Только набранные баллы
                </c:when>
                <c:when test="${sessionScope.creating_test.showResult == 'NOTHING'}">
                    Не показывать результаты
                </c:when>
            </c:choose>
        </p>
    </div>

    <div class="btn-group">
        <form method="post">
            <button type="submit" name="action" value="home" class="btn">Вернуться на главную</button>
        </form>
    </div>
</div>
</body>
</html>