<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<hr>
<h3><a href="meals?action=create">¬¥¬à¬Ò¬Ñ¬Ó¬Ú¬ä¬î ¬ß¬à¬Ó¬å¬ð ¬Ö¬Õ¬å</a></h3>
<hr>
<table border=1>
    <thead>
    <tr>
        <th>¬¥¬Ñ¬ä¬Ñ/¬Ó¬â¬Ö¬Þ¬ñ</th>
        <th>¬°¬á¬Ú¬ã¬Ñ¬ß¬Ú¬Ö</th>
        <th>¬¬¬Ñ¬Ý¬à¬â¬Ú¬Ú</th>
        <th colspan=2></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <input type="hidden"<c:out value="${meal.id}" />>
            <td><c:out value="${meal.dateTime.format(formatter)}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">¬ª¬Ù¬Þ¬Ö¬ß¬Ú¬ä¬î</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">¬µ¬Õ¬Ñ¬Ý¬Ú¬ä¬î</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>
