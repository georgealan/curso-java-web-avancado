<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Telefone</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<h1 class="headers-center">Cadastro de Telefone</h1>
	<h3 class="headers-center hnivel3">${msg}</h3>
	<form action="salvarTelefones" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário:</td>
						<td><input type="text" id="user" name="user"
							value="${userEscolhido.id}" readonly="true" /></td>
						<td><input type="text" id="nome" name="nome"
							value="${userEscolhido.nome}" readonly="true" /></td>
					</tr>
					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" value="" /></td>
						<td><select id="tipo" name="tipo" style="width: 173px;">
								<option>Celular</option>
								<option>Casa</option>
								<option>Trabalho</option>
								<option>Recado</option>
								<option>Outros</option>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="salvar" style="width: 173px;" />

						</td>
						<td><input type="submit" value="voltar" style="width: 173px;"
							onclick="document.getElementById('formUser').action = 'salvarTelefones?acao=voltar'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<!-- Container responsável por listar e mostrar na tela os dados dos telefones gravados no banco, ele puxa e mostra 
	os dados na tela, por meio de um foreach. -->
	<div class="container">
		<table class="responsive-table">
			<caption>Telefones Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Número</th>
				<th>Tipo</th>
				<th>Excluir</th>
			</tr>
			<c:forEach items="${telefone}" var="fone">
				<tr>
					<td><c:out value="${fone.id}" /></td>
					<td><c:out value="${fone.numero}" /></td>
					<td><c:out value="${fone.tipo}" /></td>
					<td><a
						href="salvarTelefones?user=${fone.usuario}&acao=deleteFone&foneId=${fone.id}"
						onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="Excluir" title="Excluir"
							width="32px" height="32px" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- Validações via front-end por Javascript. -->
	<script type="text/javascript">
		/*
			function validarCampos() {
				if (document.getElementById("numero").value === '') {
					alert('Informe o número!');
					return false;
				} else if (document.getElementById("tipo").value === '') {
					alert('Informe o tipo!');
					return false;
				}
				return true;
			}
		 */
	</script>
</body>
</html>