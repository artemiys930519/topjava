<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="style.css" rel="stylesheet" type="text/css">
    <title>EditMeal</title>
</head>
<body>
<h2><a href="">Home</a></h2>
<h3>Edit Meal</h3>
<hr>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.UserMeal"/>
    <fmt:parseDate  pattern="yyyy-MM-dd'T'HH:mm" value="${ userMeal.dateTime }" var="parsedDateTime" type="both"/>

    <form method="POST" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime: </dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description: </dt>
            <dd><input type="text" name="description" value="${meal.description}" size="40"></dd>
        </dl>
        <dl>
            <dt>Calories: </dt>
            <dd><input type="number" name="calories" value="${meal.calories}"> </dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</body>
</html>
