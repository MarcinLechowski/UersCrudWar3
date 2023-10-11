<%--
  Created by IntelliJ IDEA.
  User: kaer
  Date: 10.10.2023
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Potwierdzenie usuwania użytkownika</title>
</head>
<body>
<h1>Potwierdź usuwanie użytkownika</h1>
<p>Czy na pewno chcesz usunąć użytkownika o ID ${user.id}?</p>
<form method="post" action="delete-user">
    <input type="hidden" name="id" value="${user.id}" />
    <input type="submit" value="Usuń" />
    <a href="list-users">Anuluj</a>
</form>
</body>
</html>