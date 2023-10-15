<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE-edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Dashboard</title>

    <link href="../theme/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value="/theme/css/sb-admin-2.css"/>" rel="stylesheet">

</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="../theme/index.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">SB Admin <sup>2</sup></div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <a class="nav-link" href="${pageContext.request.contextPath}/theme/index.jsp">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>Dashboard</span>
            </a>
        </li>

    </ul>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">

            <!-- End of Topbar -->
            <%@ include file="../theme/header.jsp" %>

            <head>
                <meta charset="UTF-8">
                <title>Potwierdzenie usuwania użytkownika</title>
            </head>
            <body>
            <h1>Potwierdź usuwanie użytkownika</h1>
            <p>Czy na pewno chcesz usunąć użytkownika o ID ${user.id}?</p>
            <form method="post" action="${pageContext.request.contextPath}/users/delete">
                <input type="hidden" name="id" value="${user.id}" />
                <input type="submit" value="Usuń" />
            </form>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/users/delete.jsp" method="post">
                </form>
            <a href="${pageContext.request.contextPath}/users/list" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Analuj</a>
            </div>
            </body>
        </div>
    </div>
</body>
</html>
