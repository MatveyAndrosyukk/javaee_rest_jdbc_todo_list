<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>О разработчике</title>
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
<img style="border-radius: 50%; width: 250px; height: 200px;" src="<%=request.getContextPath()%>/images/developer.jpg"
     alt="Developer Photo"><br>
<hr/>
<h2>Разработчик: Андросюк Матвей</h2>
<img style="width: 20px; height: 20px;" src="<%=request.getContextPath()%>/images/github_logo.png"
     alt="My tasks button">
GitHub: <a href="https://github.com/MatveyAndrosyukk">перейти.</a><br>
<img style="width: 20px; height: 20px;" src="<%=request.getContextPath()%>/images/linkedin_logo.png"
     alt="My tasks button">
LinkedIn: <a href="https://www.linkedin.com/in/matvey-androsyuk-b7b012240">перейти.</a><br>
<hr/>
<h3>Всем успехов в программировании и побольше интересных проектов ! :)</h3><br>
</body>
</html>
