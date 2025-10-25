<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <h1>Создайте свой первый тест!</h1>

    <form method="post">
        <div class="btn-group">
            <button type="submit" name="choice" value="create_test" class="btn">
                Создать тест
            </button>
        </div>
    </form>
</div>
</body>
</html>