<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>

    <link href="style.css" rel="stylesheet" type="text/css">
    <title>Meal</title>
</head>
<body>
<c:if test="${meal.id>0}">
    <table>
        <tr class="main">
            <td>Time</td>
            <td>Description</td>
            <td>Calories</td>
        </tr>
        <tr>
            <td><fmt:parseDate  pattern="yyyy-MM-dd'T'HH:mm" value="${ meal.dateTime }" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </table>
</c:if>
    <fmt:parseDate  pattern="yyyy-MM-dd'T'HH:mm" value="${ meal.dateTime }" var="parsedDateTime" type="both"/>

    <form method="POST" action='meals' name="frmAddMeal">
        Meal DateTime : <input type="text" name="dateTime" placeholder="02-04-2014 12:20" value="<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${ parsedDateTime }"/>" /> <br />
        Meal Description : <input type="text" name="description" value="<c:out value="${meal.description}" />" /> <br />
        Meal Calories : <input type="text" name="calories" value="<c:out value="${meal.calories}" />" /> <br />
        <input type="hidden" name="mealId" value="<c:out value="${meal.id}" />"/>
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
