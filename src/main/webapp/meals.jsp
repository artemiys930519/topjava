<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>MealsList</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>MealsList</h2>
<table>
    <tr class="main">
        <td>ID</td>
        <td>Time</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Exceed</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr>
            <td>${meal.id}</td>
            <td><fmt:parseDate  pattern="yyyy-MM-dd'T'HH:mm" value="${ meal.dateTime }" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${ parsedDateTime }" />
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <c:choose>
                <c:when test="${meal.exceed}">
                    <td class="redexceedmeal">${meal.exceed}</td>
                </c:when>
                <c:otherwise>
                    <td class="greenexceedmeal">${meal.exceed}</td>
                </c:otherwise>
            </c:choose>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert&mealId=0">Add User</a></p>
</body>
</html>
