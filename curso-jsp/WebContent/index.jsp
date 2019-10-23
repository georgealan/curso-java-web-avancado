<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Página Inicial</title>
<link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
	<div class="Login-page">
		<div class="form">
			<form action="LoginServlet" method="post" class="Login-form">
				Login: <input type="text" id="login" name="login"> <br>
				Senha: <input type="password" id="senha" name="senha"> <br>
				<button type="submit" value="Logar">LOGAR</button>
			</form>
		</div>
	</div>
</body>
</html>