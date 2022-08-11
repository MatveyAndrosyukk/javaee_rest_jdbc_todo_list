<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Стартовая страница</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>
    <h1>Здравствуйте, приятного пользования моим сервисом задач</h1>
    <form method="get" action="<c:out value='/tasks-menu'/>">
        <input type="submit" value="Перейти к списку всех задач">
    </form>
</body>
</html>
