<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Результаты теста</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="container container-wide">

    <h1><c:out value="${requestScope.test.name}"/></h1>

    <div class="info-block">
        <p>ID: <strong>${requestScope.test.id}</strong></p>
        <c:if test="${not empty requestScope.test.description}">
            <p>Описание: <c:out value="${requestScope.test.description}"/></p>
        </c:if>
        <p>Вопросов: ${requestScope.test.questionCount}</p>
        <c:if test="${requestScope.test.needToAnswer != null}">
            <p>Необходимо ответить на ${requestScope.test.needToAnswer}</p>
        </c:if>
    </div>

    <c:if test="${not empty requestScope.test.attempts}">

        <h2>Попытки прохождения:</h2>

            <table class="table">
                <thead>
                    <tr>
                        <th>Пользователь</th>
                        <th>Начало</th>
                        <th>Окончание</th>
                        <th>Результат</th>
                        <th>Статус</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                <c:forEach var="attempt" items="${requestScope.test.attempts}">
                    <tr class="test-row
                                ${attempt.completedAt == null ? 'row-in-progress' : attempt.success ? 'row-success' : 'row-failed'}">

                        <td class="table-text">
                                ${attempt.user.login != null ? attempt.user.login : 'Неизвестно'}
                        </td>

                        <td class="table-text">
                            <c:choose>
                                <c:when test="${attempt.startedAt != null}">
                                    <fmt:parseDate value="${attempt.startedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedStartedAt"/>
                                    <fmt:formatDate value="${parsedStartedAt}" pattern="dd.MM.yyyy HH:mm"/>
                                </c:when>
                                <c:otherwise>
                                    Не указана
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="table-text">
                            <c:choose>
                                <c:when test="${attempt.completedAt != null}">
                                    <fmt:parseDate value="${attempt.completedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedCompletedAt"/>
                                    <fmt:formatDate value="${parsedCompletedAt}" pattern="dd.MM.yyyy HH:mm"/>
                                </c:when>
                                <c:otherwise>
                                    –
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="table-text">
                            <c:if test="${attempt.score != null}">
                                ${attempt.score}
                                <c:if test="${requestScope.test.questionCount != null}">
                                    / ${requestScope.test.questionCount}
                                </c:if>
                            </c:if>
                            <c:if test="${attempt.score == null}">
                                –
                            </c:if>
                        </td>

                        <td class="table-text">
                            <c:choose>
                                <c:when test="${attempt.completedAt != null}">
                                    <c:choose>
                                        <c:when test="${attempt.success != null and attempt.success == true}">
                                            Зачтено
                                        </c:when>
                                        <c:otherwise>
                                            Не зачтено
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    В процессе
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="table-actions">
                            <c:if test="${attempt.completedAt != null}">
                                <form method="post">
                                    <input type="hidden" name="attempt_id" value="${attempt.id}">
                                    <button type="submit" class="btn-small btn-secondary" name="choice" value="view_attempt">
                                        Просмотр
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${attempt.completedAt == null}">
                                –
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
    </c:if>

    <c:if test="${empty requestScope.test.attempts}">
        <div class="info">
            <p>Пока нет попыток прохождения этого теста</p>
        </div>
    </c:if>

    <div class="btn-group">
        <form method="post">
            <button type="submit" class="btn btn-secondary" name="choice" value="home">
                Назад к списку тестов
            </button>
        </form>
    </div>
</div>
</body>
</html>