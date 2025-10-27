<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Результаты теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="container">

    <h1>
        Тест <c:out value="${requestScope.test.name}"/>
        <c:choose>
            <c:when test="${requestScope.attempt.success}">пройден успешно!</c:when>
            <c:otherwise>не пройден!</c:otherwise>
        </c:choose>
    </h1>

    <div class="info">
        <h3>Верных ответов: ${requestScope.attempt.score} из ${requestScope.test.questionCount}</h3>
    </div>


    <c:forEach var="studentAnswer" items="${requestScope.attempt.studentAnswers}" varStatus="status">
        <c:set var="question" value="${studentAnswer.question}" />

        <div class="question-block">
            <div class="question-header">
                <div class="question-text">
                    <p>${status.index + 1}. <c:out value="${question.text}"/></p>
                </div>
            </div>

            <div class="question-content">
                <div class="radio-group">
                    <c:forEach var="answer" items="${question.answerOptions}" varStatus="aStatus">
                        <div class="radio-option
                            ${answer.id == studentAnswer.selectedAnswer.id ? (studentAnswer.isCorrect ? 'correct' : 'incorrect') : ''}
                            ${answer.isCorrect ? 'correct-answer' : ''}">
                            <input type="radio"
                                   id="q${status.index}_a${aStatus.index}"
                                   disabled
                                ${answer.id == studentAnswer.selectedAnswer.id ? 'checked' : ''}>
                            <label for="q${status.index}_a${aStatus.index}" class="answer-option">
                                    ${answer.text}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

    </c:forEach>

    <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'STUDENT'}">
        <form method="post" class="btn-group">
            <button name="choice" value="home" class="btn">На главную</button>
        </form>
    </c:if>
</div>
</body>
</html>