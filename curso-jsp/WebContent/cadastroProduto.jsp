<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Format Number do jstl serve para criar mascaras nos campos de valores e outras coisas ver documentação. -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Produto</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js"
	type="text/javascript"></script>
</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<h1 class="headers-center">Cadastro de Produto</h1>
	<h3 class="headers-center hnivel3">${msg}</h3>
	<form action="salvarProduto" method="post" id="formProduto"
		onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código Produto:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${produto.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" maxlength="100"
							value="${produto.nome}" placeholder="Digite o Nome do Produto"></td>
					</tr>
					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="quantidade" name="quantidade"
							value="${produto.quantidadeEmTexto}"
							placeholder="Digite a Quantidade do Produto" data-thousands="."
							data-decimal="," data-precision="1" maxlength="12"></td>
					</tr>
					<tr>
						<td>Valor R$:</td>
						<td><input type="text" id="valor" name="valor" maxlength="20"
							value="${produto.valorEmTexto}" data-thousands="."
							data-decimal="," data-precision="2"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formProduto').action = 'salvarProduto?acao=reset'"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<!-- Container responsavel por exibir a listagem de dados na tela, é uma estrutura de repetição
	foreach. 
	* Usamos a tag fmt:formatNumber para que os valores exibidos na tela sejam fiquem corretos mostrando
	os pontos e virgulas nos locais corretos.-->
	<div class="container">
		<table class="responsive-table">
			<caption>Produtos Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Valor R$</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td style="width: 120px"><c:out value="${produto.id}" /></td>
					<td><c:out value="${produto.nome}" /></td>
					<td style="width: 120px"><fmt:formatNumber type="number"
							value="${produto.quantidade}" maxFractionDigits="1" /></td>
					<td style="width: 120px"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${produto.valor}" /></td>
					<td><a href="salvarProduto?acao=delete&produto=${produto.id}" onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"></a></td>
					<td><a href="salvarProduto?acao=editar&produto=${produto.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("nome").value === '') {
				alert('Informe o nome do produto!');
				return false;
			} else if (document.getElementById("quantidade").value === '') {
				alert('Informe a quantidade do produto!');
				return false;
			} else if (document.getElementById("valor").value === '') {
				alert('Informe o valor do produto!');
				return false;
			}
			return true;
		}
	</script>

	<script type="text/javascript">
		//Função responsavel por criar mascara de valores monetários no javascript.
		$(function() {
			$('#valor').maskMoney();
		});

		$(function() {
			$('#quantidade').maskMoney();
		});
	</script>
</body>
</html>