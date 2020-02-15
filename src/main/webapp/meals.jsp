<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<style>
    <%@include file="/WEB-INF/css/meals.css"%>
</style>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <tr>
        <th>ID</th>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Actions</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
    <c:forEach items="${meals}" var="mealTo">
        <tr class="${mealTo.excess ? 'excess' : 'normal'}">
            <td>${mealTo.id}</td>
            <td>${mealTo.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            <td>${mealTo.description} </td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=update&Id=<c:out value="${mealTo.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&Id=<c:out value="${mealTo.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<%--<p><a href="meals?action=insert">Add eating</a></p>--%>
<p></p>
<form action="meals" method="get">
    <button type="submit" name="action" value="insert">Add eating</button>
</form>
</body>
</html>