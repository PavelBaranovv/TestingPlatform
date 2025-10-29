<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Заполните вопрос</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h2>Вопрос ${sessionScope.creating_test.getQuestionCount() + 1}</h2>

    <form method="post">
        <div class="form-group">
            <label>
                <textarea name="questionText" rows="3" cols="50" placeholder="Введите текст вопроса" required>${param.questionText}</textarea>
            </label>
        </div>

        <div class="form-group">
            <label>Варианты ответов:</label>
            <c:forEach var="i" begin="0" end="${sessionScope.answers_count - 1}" varStatus="status">
                <div class="answer-option">
                        <input type="radio" name="correctAnswer" value="${i}"
                               <c:if test="${param.correctAnswer == i}">checked</c:if> required>
                    <input type="text" name="answer_${i}"
                           value="${param['answer_' += i]}"
                           placeholder="Ответ ${i + 1}" required>
                </div>
            </c:forEach>

            <div class="answer-controls">
                <button type="submit" formnovalidate name="action" value="addAnswer" class="btn-small">+</button>
                <button type="submit" formnovalidate name="action" value="removeAnswer" class="btn-small">–</button>
            </div>
        </div>

        <div class="btn-group">
            <button type="submit" name="action" value="nextQuestion" class="btn">Добавить ещё один вопрос</button>
            <button type="submit" name="action" value="finish" class="btn btn-secondary">Завершить</button>
        </div>
    </form>
</div>
</body>
</html>