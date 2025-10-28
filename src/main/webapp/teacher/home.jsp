<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <c:if test="${empty requestScope.tests}">
        <h1>Добро пожаловать!</h1>
    </c:if>

    <c:if test="${not empty requestScope.tests}">
        <h1>Созданные тесты</h1>
        <div class="tests-list">
            <table class="tests-table">
                <thead>
                <tr>
                    <th>Имя теста</th>
                    <th>ID</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="test" items="${requestScope.tests}">
                    <tr class="test-row">
                        <td class="test-name">
                            <a href="${pageContext.request.contextPath}/teacher/test-info/${test.id}">${test.name}</a>
                        </td>
                        <td class="test-id">
                            <div class="id-block-small">${test.id}</div>
                        </td>
                        <td class="test-actions">
                            <form method="post">

                                <input type="hidden" name="test_id" value="${test.id}">

                                <button type="submit" class="btn-small btn-results" name="choice" value="results">
                                    Результаты
                                </button>

                                <button type="submit" class="btn-small btn-delete" name="choice" value="delete_test">
                                    Удалить
                                </button>

                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <form method="post">
        <div class="btn-group">
            <button type="submit" name="choice" value="create_test" class="btn">
                Создать новый тест
            </button>
        </div>
    </form>
</div>
</body>
</html>