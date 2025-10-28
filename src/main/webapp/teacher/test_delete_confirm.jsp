<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Подтвердите удаление теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
    <div class="container">
        <h1>Вы уверены, что хотите удалить тест?</h1>

        <div class="error-message">
            <p>Он станет недоступным для прохождения, а также удалятся сохраненные попытки</p>
        </div>

        <div class="btn-group">
            <form method="post">
                <button type="submit" class="btn btn-warning" name="choice" value="confirm">Да, удалить тест</button>
                <button type="submit" class="btn btn-secondary" name="choice" value="cancel">Отменить</button>
            </form>
        </div>
    </div>
</body>
</html>
