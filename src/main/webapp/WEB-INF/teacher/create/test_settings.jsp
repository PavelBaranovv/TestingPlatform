<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Настройка теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="container">

    <h1>Тест почти готов!</h1>

    <div class="info">
        <p>Осталось указать его настройки</p>
        <p>Вопросов в тесте: ${sessionScope.creating_test.questionCount}</p>
    </div>

    <form method="post">
        <div class="form-group">
            <label>На сколько из них нужно ответить, чтобы успешно пройти тест?
            <input type="number" name="need_to_answer" min="0" max="${sessionScope.creating_test.questionCount}" value="1" required>
            </label>
        </div>

        <div class="form-group">
            <div class="info">
                <p>Отображение результата:</p>
            </div>

            <div class="radio-group">
                <div class="radio-option">
                    <input type="radio" id="result1" name="show_result" value="MISTAKES_AND_CORRECTS" checked>
                    <label for="result1">Ошибки и верные ответы</label>
                </div>
                <div class="radio-option">
                    <input type="radio" id="result2" name="show_result" value="ONLY_MISTAKES">
                    <label for="result2">Только ошибки</label>
                </div>
                <div class="radio-option">
                    <input type="radio" id="result3" name="show_result" value="ONLY_SCORE">
                    <label for="result3">Только набранные баллы</label>
                </div>
                <div class="radio-option">
                    <input type="radio" id="result4" name="show_result" value="NOTHING">
                    <label for="result4">Не показывать результаты</label>
                </div>
            </div>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn">Готово</button>
        </div>
    </form>
</div>
</body>
</html>