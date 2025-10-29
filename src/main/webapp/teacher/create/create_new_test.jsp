<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Создание теста</h1>

    <form method="post">
        <div class="form-group">
            <label>
                Название: <input type="text" name="test_name" required>
            </label>
        </div>

        <div class="form-group">
            <label>
                Описание:
                <textarea name="test_description" rows="3" cols="50" placeholder="Введите описание к тесту (необязательно)">${param.questionText}</textarea>
            </label>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn">Перейти к заполнению вопросов</button>
        </div>
    </form>
</div>
</body>
</html>