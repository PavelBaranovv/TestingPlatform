<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Пройденные тесты</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="container container-wide">

    <h1>Попытки прохождения тестов</h1>

    <c:if test="${not empty requestScope.attempts}">
        <table class="table">
            <thead>
                <tr>
                    <th>Тест</th>
                    <th>Начало</th>
                    <th>Окончание</th>
                    <th>Результат</th>
                    <th>Статус</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
            <c:forEach var="attempt" items="${requestScope.attempts}">
                <tr class="test-row
                                ${attempt.test.showResult == 'NOTHING' ? '' :
                                attempt.completedAt == null ? 'row-in-progress' :
                                attempt.success ? 'row-success' : 'row-failed'}">

                    <td class="table-text">
                            ${attempt.test.name != null ? attempt.test.name : 'Без названия'}
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
                        <c:choose>
                            <c:when test="${attempt.test.showResult == 'NOTHING' or attempt.score == null}">
                                –
                            </c:when>
                            <c:otherwise>
                                ${attempt.score}
                                <c:if test="${attempt.test.questionCount != null}">
                                    / ${attempt.test.questionCount}
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td class="table-text">
                        <c:choose>
                            <c:when test="${attempt.test.showResult == 'NOTHING'}">
                                Отправлен
                            </c:when>
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

    <c:if test="${empty requestScope.attempts}">
        <div class="info">
            <p>Вы ещё не проходили тестов</p>
        </div>
    </c:if>

    <div class="btn-group">
        <form method="post">
            <button type="submit" class="btn btn-secondary" name="choice" value="home">
                Вернуться на главную
            </button>
        </form>
    </div>
</div>
</body>
</html>