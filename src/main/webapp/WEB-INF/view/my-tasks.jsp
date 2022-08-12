<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Мои задачи</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>
<a href="<c:url value="/logout"/>">
    <img style="width: 90px; height: 45px;" src="<%=request.getContextPath()%>/images/logout_button.png"
         alt="Logout Button">
</a>
<a href="<c:url value="/tasks-menu"/>">
    <img style="width: 90px; height: 45px;" src="<%=request.getContextPath()%>/images/show_all_tasks_button.png"
         alt="All Tasks Button">
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
<h2>Мои задачи</h2>
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
