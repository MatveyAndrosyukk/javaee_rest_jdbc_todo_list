<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Редактирование задачи</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>

</head>
<body>
<div>Название: <c:out value="${requestScope.task.title}"/></div>
<div>Описание: <c:out value="${requestScope.task.description}"/></div>
<br/>

<form method="post" action="<c:url value='/update-task'/>">
    <label>Новое название: <input type="text" name="title" required/></label><br>
    <label>Новое описание: <input type="text" name="description" required/></label><br>
    <label>
        <input type="number" hidden name="id" value="${requestScope.task.id}"/>
    </label>
    <input type="submit" value="Ok" name="Ok"><br>
</form>
</body>
</html>