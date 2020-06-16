<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .hr {
            display:inline-block;
        }
    </style>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<ul>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Моя еда</h2>
    <form method="get" action="meals">
        <div>
            <div class="hr">
                <dl>От даты (включая):<input type="date" name="startDate"></dl>
            </div>
            <div class="hr">
                <dl>От времени (включая): <input type="time" name="startTime"></dl>
            </div>
            <br/>
            <div class="hr">
                <dl>До даты (включая):<input type="date" name="endDate"></dl>
            </div>
            <div class="hr">
                <dl>До времени (исключая):<input type="time" name="endTime"></dl>
            </div>

        </div>
        <button type="submit">Фильтровать</button>
        <button onclick="window.history.back()" type="button">Отменить</button>
    </form>


    <button><a href="meals?action=create"><i class="material-icons">add</i></a></button>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <%--<th>id</th>--%>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <%--<th>userId</th>--%>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.To.MealTo"/>
            <tr style="color:${meal.excess ? 'red' : 'green'}">
                    <%--<td>${meal.id}</td>--%>
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                    <%--<td>${meal.userId}</td>--%>
                <td><a href="meals?action=update&id=${meal.id}"><i class="material-icons">update</i></a></td>
                <td><a href="meals?action=delete&id=${meal.id}"><i class="material-icons">delete</i></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</ul>
</html>

