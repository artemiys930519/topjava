<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <c:forEach var="meal" items="${meals}">
        <tr>
            <td>${meal.timeToString}</td>
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
    </c:forEach>
</table>
</body>
</html>
