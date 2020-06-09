<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add new meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>MealsForm</h2>
    <br/>
    <table border="2">
    <tr>
        <td>
            <form action = "#" method="post">
                <div class="form-group col-md-3">
                    <label></label>
                    <input name="id" type=hidden readonly="readonly" value="${meal.id}" placeholder="${meal.id}">
                </div>
                <div class="form-group col-md-3">
                    <label>DateTime</label>
                    <input name="dateTime" type="datetime-local"
                           value="${meal.dateTime}" placeholder="${LocalDataTime.now()}">
                </div>
                <div class="form-group col-md-3">
                    <label>Description</label>
                    <input name="description" value="${meal.description}" placeholder="${meal.description}">
                </div>
                <div class="form-group col-md-3">
                    <label>Calories</label>
                    <input name="calories" min="0" step="1" type="number"
                           value="${meal.calories}" placeholder="${meal.calories}">
                </div>
                <input type="submit" value="Ввести" />
            </form>
        </tr>
</table>
</body>
</html>
