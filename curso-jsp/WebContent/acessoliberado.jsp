<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rodape Page</title>
<link rel="stylesheet" href="resources/css/acesso.css">
</head>
<body>
	<div class="centraliza">
		<jsp:setProperty property="*" name="calcula" />
		<h3>Seja bem vindo ao sistema em JSP</h3>

		<a href="salvarUsuario?acao=Listartodos"><img
			src="resources/img/usuario.png" width="150px" height="150px"
			alt="Cadastro de Usuarios" title="Cadastro de Usuarios"></a> <a
			href="salvarProduto?acao=listartodos"><img width="150px"
			height="150px" title="Cadastro de Produto" alt="Cadastro de Produto"
			src="resources/img/produto.png"> </a>
	</div>
</body>
</html>