<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Логин</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>
<h2>Аутентификация</h2>
<p><c:out value="${requestScope.credentialsError}"/></p>
<form method="post" action="<c:url value='/login'/>">
    <p>
        <label>
            <input type="text" name="email" placeholder="Введите email" required>
        </label>
    </p>
    <p>
        <label>
            <input type="password" name="password" placeholder="Введите пароль" required>
        </label>
    </p>
    <input type="submit" value="Войти">
    <input type="reset" value="Очистить">
</form>
<br>
<form method="get" action="<c:url value='/registration'/>">
    <input type="submit" value="Зарегистрироваться">
</form>
</body>
</html>
