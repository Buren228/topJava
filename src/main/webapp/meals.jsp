<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>

<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <h2>${param.action == 'filter' ? 'filter meals' : null}</h2>
    <jsp:useBean id = "timeUtil" class="ru.javawebinar.topjava.util.DateTimeUtil" scope="request"/>
    <form method="post" action="filt">
        <dl>
            <dt>От даты:</dt>
            <dd><input type="date" value="${timeUtil.startDate}" name="startDate" required></dd>
            <dt>До даты:</dt>
            <dd><input type="date" value="${timeUtil.endDate}" name="endDate" required></dd>
        </dl>
        <dl>
            <dt>От времени:</dt>
            <dd><input type="time" value="${timeUtil.startTime}" name="startTime" required></dd>
            <dt>До времени:</dt>
            <dd><input type="time" value="${timeUtil.endTime}" name="endTime" required></dd>
        </dl>
        <dl>
            <dd>
                <button type="submit"> Отфильтровать</button>
            </dd>
        </dl>
    </form>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>