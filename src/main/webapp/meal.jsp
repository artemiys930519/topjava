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
            <td>Exceed</td>
        </tr>
        <tr>
            <td><fmt:parseDate  pattern="yyyy-MM-dd'T'HH:mm" value="${ meal.dateTime }" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
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
        </tr>
    </table>
</c:if>
    <fmt:parseDate  pattern="yyyy-MM-dd'T'HH:mm" value="${ meal.dateTime }" var="parsedDateTime" type="both"/>

    <form method="POST" action='meals' name="frmAddMeal">
        Meal ID : <input type="text" name="mealId"
                         value="<c:out value="${meal.id}" />" /> <br />
        Meal DateTime : <input type="text" name="dateTime"
                         value="<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${ parsedDateTime }"/>" /> <br />
        Meal Description : <input
            type="text" name="description"
            value="<c:out value="${meal.description}" />" /> <br />
        Meal Calories : <input
            type="text" name="calories"
            value="<c:out value="${meal.calories}" />" /> <br />
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
