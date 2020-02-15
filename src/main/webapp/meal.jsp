<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%--    <form method="post" action="meals?action=submit">--%>
<form action="meals" method="post">
    <table cellspacing="0" cellpadding="4">
        <tr>
<%--            <td align="right">ID</td>--%>
            <td><input hidden type="number" name="Id" value="${meal.id}" placeholder="${meal.id}" readonly/></td>
        </tr>
        <tr>
            <td align="right">DateTime</td>
            <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}" placeholder="${meal.dateTime}"/>
            </td>
        </tr>
        <tr>
            <td align="right">Description</td>
            <td><input type="text" name="description" value="${meal.description}" placeholder="${meal.description}"/>
            </td>
        </tr>
        <tr>
            <td align="right">Calories</td>
            <td><input type="number" name="calories" value="${meal.calories}" placeholder="${meal.calories}"/></td>
        </tr>
    </table>
    <button type="submit" name="action" value="save">Save</button>
</form>

</body>
</html>
