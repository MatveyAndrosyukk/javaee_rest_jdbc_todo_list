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
<div>Название: <c:out value="${requestScope.task.title}"/> </div>
<div>Описание: <c:out value="${requestScope.task.description}"/> </div>
<div>Срок выполнения: <c:out value="${requestScope.task.deadline_date}"/> </div>
<div>Исполнитель: <c:out value="${requestScope.task.executor.name}"/> <c:out value="${requestScope.task.executor.surname}"/>  </div>
<div>Статус выполнения: <c:out value="${requestScope.task.done ? 'выполнена' : 'не выполнена'}"/> </div>
<br/>

<p><c:out value="${requestScope.userErrorMessage}"/></p>
<form method="post" action="<c:url value='/update-task'/>">
    <label>Новое название:
        <input type="text" name="title" value="<c:out value="${requestScope.enteredFields.title}"/>"/>
    </label><br>
    <label>Новое описание:
        <input type="text" name="description" value="<c:out value="${requestScope.enteredFields.description}"/>"/>
    </label><br>
    <label>Новый срок выполнения:
        <input type="date" name="deadline_date" value="<c:out value="${requestScope.enteredFields.deadline_date}"/>"/>
    </label><br>
    <label>Новый статус выполнения:
        <input type="checkbox" name="done" value="<c:out value="${requestScope.enteredFields.done}"/>"/>
    </label><br>
    <label>Новый исполнитель:
        <input type="text" name="username" value="<c:out value="${requestScope.enteredFields.executor.username}"/>"/>
    </label><br>
    <label>
        <input type="number" hidden name="id" value="${requestScope.task.id}"/>
    </label>
    <input type="submit" value="Ok" name="Ok"><br>
</form>
</body>
</html>