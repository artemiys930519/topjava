<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>MealList</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>MealsList</h2>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach var="meal" items="${mealList}">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed" />
        <tr class="${meal.exceed ? 'redexceedmeal':'greenexceedmeal'}">
            <td><fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${ meal.dateTime }" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="mealList?action=update&id=${meal.id}">Update</a></td>
            <td><a href="mealList?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="mealList?action=create">Add User</a></p>
</body>
</html>
