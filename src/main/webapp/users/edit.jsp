<%--
  Created by IntelliJ IDEA.
  User: kaer
  Date: 10.10.2023
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formularz użytkownika</title>
</head>
<body>

<h1>Formularz użytkownika</h1>
<form method="post" action="save-user">
  <input type="hidden" name="id" value="${user.id}" />
  <label for="firstName">Imię:</label>
  <input type="text" name="firstName" id="firstName" value="${user.firstName}" />
  <br>
  <label for="lastName">Nazwisko:</label>
  <input type="text" name="lastName" id="lastName" value="${user.lastName}" />
  <br>
  <input type="submit" value="Zapisz" />
</form>

</body>
</html>
