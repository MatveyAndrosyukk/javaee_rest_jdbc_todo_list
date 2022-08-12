<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: matve
  Date: 05.08.2022
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Регистрация</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>
<h2>Регистрация пользователя</h2>
<p><c:out value="${requestScope.userRegistrationSuccess}"/></p>
<p><c:out value="${requestScope.emailIncorrectlyFilled}"/></p>
<form method="post" action="<c:url value='/registration'/>">
    <p>
        <label>
            <input type="text" name="username" placeholder="Введите username" required/>
        </label>
    </p>

    <p>
        <label>
            <input type="password" name="password" placeholder="Введите пароль" required/>
        </label>
    </p>

    <p>
        <label>
            <input type="text" name="name" placeholder="Введите имя" required/>
        </label>
    </p>

    <p>
        <label>
            <input type="text" name="surname" placeholder="Введите фамилию" required/>
        </label>
    </p>

    <p>
        <label>
            <input type="email" name="email" placeholder="Введите e-mail" required/>
        </label>
    </p>


    <input type="submit" value="Отправить">
    <input type="reset" value="Очистить">
</form>

<br>

<form method="get" action="<c:url value='/login'/>">
    <input type="submit" value="Вернуться">
</form>
</body>
</html>
