<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Прохождение теста</title>
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

    <form method="post">
        <c:forEach var="question" items="${sessionScope.solving_test.questions}" varStatus="qStatus">
            <div class="question-block">
                <div class="question-header">
                    <div class="question-text">
                        <p>${qStatus.index + 1}. <c:out value="${question.text}"/></p>
                    </div>
                </div>

                <div class="question-content">


                    <div class="radio-group">
                        <c:forEach var="answer" items="${question.answerOptions}" varStatus="aStatus">
                            <div class="radio-option">
                                <input type="radio"
                                       id="q${qStatus.index}_a${aStatus.index}"
                                       name="question_${question.id}"
                                       value="${answer.id}"
                                       required>
                                <label for="q${qStatus.index}_a${aStatus.index}" class="answer-option">
                                        ${answer.text}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>

        <div class="btn-group">
            <button type="submit" class="btn" name="choice" value="finish_test">Отправить все и завершить</button>
        </div>
    </form>
</div>
</body>
</html>