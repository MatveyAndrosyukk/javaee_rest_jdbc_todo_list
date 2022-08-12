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
<a href="<c:url value="/logout"/>">
    <img style="width: 90px; height: 45px;" src="<%=request.getContextPath()%>/images/logout_button.png"
         alt="Logout Button">
</a>
<a href="<c:url value="/my-tasks"/>">
    <img style="width: 90px; height: 45px;" src="<%=request.getContextPath()%>/images/my_tasks_button.png"
         alt="My Tasks Button">
</a>
<a href="<c:url value="/developer-info"/>">
    <img style="width: 90px; height: 45px;" src="<%=request.getContextPath()%>/images/developer_info_button.png"
         alt="Developer Info Button">
</a>
<hr/>
<h2>Получить JSON задачи по id</h2>
<form method="get" action="<c:url value='/get-task'/>">
    <label>ID задачи
        <input type="number" name="id">
    </label><br>
    <input type="submit" value="Получить данные задачи" name="Ok"><br>
</form>
<hr/>
<h2>Создание новой задачи</h2>
<p><c:out value="${requestScope.userErrorMessage}"/></p>
<p><c:out value="${requestScope.invalidDateMessage}"/></p>
<p><c:out value="${requestScope.invalidRequestMessage}"/></p>
<form method="post" action="<c:url value='/add-task'/>">
    <label>Название
        <input type="text" name="title" value="<c:out value="${requestScope.enteredFields.title}"/>" required/>
    </label><br>
    <label>Описание
        <input type="text" name="description" value="<c:out value="${requestScope.enteredFields.description}"/>"
               required/>
    </label><br>
    <label>Срок выполнения
        <input type="date" name="deadline_date" value="<c:out value="${requestScope.enteredFields.deadline_date}"/>"
               required/>
    </label><br>
    <label>Выполнена
        <input type="checkbox" name="done" value="<c:out value="${requestScope.enteredFields.done}"/>"/>
    </label><br>
    <label>Исполнитель
        <input type="text" name="username" value="<c:out value="${requestScope.enteredFields.executor.username}"/>"
               required/>
    </label><br>
    <input type="submit" value="Ok" name="Ok"/>
</form>
<hr/>
<h2>Все задачи</h2>
<c:forEach var="task" items="${requestScope.tasks}">
    <ul>
        Идентификационный номер: <c:out value="${task.id}"/> <br>
        Название: <c:out value="${task.title}"/> <br>
        Описание: <c:out value="${task.description}"/> <br>
        Срок выполнения: <c:out value="${task.deadline_date}"/> <br>
        Исполнитель: <c:out value="${task.executor.name}"/> <c:out value="${task.executor.surname}"/> <br>
        Статус выполнения: <c:out value="${task.done ? 'выполнена' : 'не выполнена'}"/> <br>
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
</body>
</html>
