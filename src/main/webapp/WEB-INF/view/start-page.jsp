<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Сервис задач</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>
<h2>Все задачи</h2>
<c:forEach var="task" items="${requestScope.tasks}">
    <ul>
        Название: <c:out value="${task.title}"/> <br>
        Описание: <c:out value="${task.description}"/> <br>

        <form method="get" action="<c:url value='/update-task'/>">
            <label>
                <input type="number" hidden name="id" value="${task.id}">
            </label>
            <input type="submit" value="Редактировать">
        </form>
        <form method="post" action="<c:url value='/delete-task'/>">
            <label>
                <input type="number" hidden name="id" value="${task.id}">
            </label>
            <input type="submit" value="Удалить">
        </form>
    </ul>
    <hr/>
</c:forEach>
<h2>Создание новой задачи</h2>
<form method="post" action="<c:url value='/add-task'/>">
    <label>Название <input type="text" name="title"/></label><br>
    <label>Описание <input type="text" name="description"/></label><br>
    <input type="submit" value="Ok" name="Ok"/>
</form>
<h2>Получить JSON задачи по id</h2>
<form method="get" action="<c:url value='/get-task'/>">
    <label>ID задачи <input type="number" name="id"></label><br>
    <input type="submit" value="Получить данные задачи" name="Ok"><br>
</form>
</body>
</html>
