<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Тест</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<div class="container">
    <h1>
        <c:out value="${sessionScope.solving_test.name}"/>
    </h1>

    <c:if test="${not empty sessionScope.solving_test.description}">
        <div class="info">
            <p>
                <c:out value="${sessionScope.solving_test.description}"/>
            </p>
        </div>
    </c:if>


    <div class="info-block">
        <p>Вопросов в тесте: ${sessionScope.solving_test.questionCount}</p>
        <p>Необходимо верно ответить на ${sessionScope.solving_test.needToAnswer}</p>
    </div>

    <form method="post" class="btn-group">
        <button type="submit" class="btn" name="choice" value="start_test">Приступить</button>
        <button type="submit" class="btn btn-secondary" name="choice" value="home">Вернуться на главную</button>
    </form>
</div>
</body>
</html>
