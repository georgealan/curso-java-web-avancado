<%@page import="beans.BeanCursoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de usuario</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<h1 class="headers-center">Cadastro de Usuario</h1>
	<h3 class="headers-center hnivel3">${msg}</h3>
	<form action="salvarUsuario" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;"
		enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" name="id"
							value="${user.id}" /></td>

						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep"
							value="${user.cep}" onblur="consultaCep();"
							placeholder="Digite o CEP" /></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login"
							value="${user.login}" placeholder="Digite o seu nome de Login" /></td>

						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua"
							value="${user.rua}" placeholder="Digite a sua Rua" /></td>
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha"
							value="${user.senha}" placeholder="Digite a sua senha" /></td>

						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro"
							value="${user.bairro}" placeholder="Digite o seu Bairro" /></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${user.nome}" placeholder="Informe seu Nome Completo" /></td>

						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade"
							value="${user.cidade}" placeholder="Digite a sua Cidade" /></td>
					</tr>
					<tr>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado"
							value="${user.estado}" placeholder="Digite o seu estado" /></td>

						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge"
							value="${user.ibge}" /></td>
					</tr>
					
					<tr>
						<td>Ativo:</td>
						<td><input type="checkbox" id="ativo" name="ativo"
							<%
							/*Serve para adicionar atributo HTML dinamicamente dependendo de condições.*/
								if(request.getAttribute("user") != null){
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
									if(user.isAtivo()){
										out.print(" ");
										out.print("checked=\"checked\"");
										out.print(" ");
									}
								}
							%>
						></td>
					</tr>
					<tr>
						<td>Foto:</td>
						<td><input type="file" name="foto"></td>
						<td>Sexo:</td>
						<td><input type="radio" name="sexo" 
							<%
								if(request.getAttribute("user") != null){
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
									if(user.getSexo().equalsIgnoreCase("masculino")){
										out.print(" ");
										out.print("checked=\"checked\"");
										out.print(" ");
									}
								}
							%>
						value="masculino">Masculino</input>
						<input type="radio" name="sexo" 
							 <%
								if(request.getAttribute("user") != null){
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
									if(user.getSexo().equalsIgnoreCase("feminino")){
										out.print(" ");
										out.print("checked=\"checked\"");
										out.print(" ");
									}	
								}
							 %>
						
						value="feminino">Feminino</input>
						</td>
					</tr>

					<tr>
						<td>Curriculo:</td>
						<td><input type="file" name="curriculo" value="curriculo"></td>
						<td>Perfil:</td>
						<td>
							<select id="perfil" name="perfil">
								<option value="nao_informado">[--SELECIONE--]</option>
								<option value="administrador"
									<%
										if(request.getAttribute("user") != null){
											BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
											if(user.getPerfil().equalsIgnoreCase("administrador")){
												out.print(" ");
												out.print("selected=\"selected\"");
												out.print(" ");
											}	
										}
								    %>
								>Administrador</option>
								<option value="secretario"
									<%
										if(request.getAttribute("user") != null){
											BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
											if(user.getPerfil().equalsIgnoreCase("secretario")){
												out.print(" ");
												out.print("selected=\"selected\"");
												out.print(" ");
											}	
										}
								    %>
								>Secretário</option>
								<option value="gerente"
									<%
										if(request.getAttribute("user") != null){
											BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
											if(user.getPerfil().equalsIgnoreCase("gerente")){
												out.print(" ");
												out.print("selected=\"selected\"");
												out.print(" ");
											}	
										}
								    %>
								>Gerente</option>
								<option value="funcionario"
									<%
										if(request.getAttribute("user") != null){
											BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
											if(user.getPerfil().equalsIgnoreCase("funcionario")){
												out.print(" ");
												out.print("selected=\"selected\"");
												out.print(" ");
											}	
										}
								    %>
								>Funcionario</option>
							</select>
						</td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 173px;" /></td>
						<td></td>
						<td><input type="submit" style="width: 173px;"
							value="Cancelar"
							onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'" /></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<!-- Campo formulário de consulta dinâmica -->
	<form method="post" action="servletPesquisa">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Descrição</td>
						<td><input type="text" id="descricaoconsulta" name="descricaoconsulta"></td>
						<td><input type="submit" value="Pesquisar"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<div class="container">
		<table class="responsive-table">
			<caption>Usuarios Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Foto</th>
				<th>Curriculo</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td><c:out value="${user.id}" /></td>

					<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
					<!-- Validações para exibir imagem padrão no lugar da foto se o usuario não especificar uma imagem padrão. -->
						<td><a
							href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
								src='<c:out value="${user.fotoBase64Miniatura}"/>' alt="Imagem User"
								title="Imagem User" width="32px" height="32px" /> </a></td>
					</c:if>
					<c:if test="${user.fotoBase64Miniatura == null}">
						<td><img alt="Imagem User" src="resources/img/userpadrao.png"
							width="32px" height="32px" onclick="alert('Não possui imagem')">
						</td>
					</c:if>
					<!-- Validações para exibir imagem padrão no lugar do .pdf se o usuario não especificar uma imagem padrão. -->
					<c:if test="${user.curriculoBase64.isEmpty() == false}">
						<td><a
							href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
								alt="Curriculo" src="resources/img/pdf.png" width="32px"
								height="32px"> </a></td>
					</c:if>
					<c:if test="${user.curriculoBase64 == null}">
						<td><img alt="Curriculo" src="resources/img/pdf.png"
							width="32px" height="32px"
							onclick="alert('Não possui curriculo')"></td>
					</c:if>

					<td><c:out value="${user.nome}" /></td>
					<!-- Botão Adicionar Telefone -->
					<td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img
							src="resources/img/telefone.png" alt="Telefones"
							title="Telefones" width="32px" height="32px" /></a></td>
					<!-- Botão Deletar Usuario-->		
					<td><a href="salvarUsuario?acao=delete&user=${user.id}" onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="Excluir" title="Excluir"
							width="32px" height="32px" /></a></td>
					<!-- Botão Editar Usuario-->		
					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
							src="resources/img/editar.png" alt="Editar" title="Editar"
							width="32px" height="32px" /></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("login").value === '') {
				alert('Informe o login!');
				return false;
			} else if (document.getElementById("senha").value === '') {
				alert('Informe a senha!');
				return false;
			} else if (document.getElementById("nome").value === '') {
				alert('Informe o nome!');
				return false;
			} else if (document.getElementById("telefone").value === '') {
				alert('Informe o telefone!');
				return false;
			}
			return true;
		}

		//Usamos o Web Service ViaCEP para ter a consulta de CEP no sistema.
		function consultaCep() {
			var cep = $("#cep").val();

			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} //end if.
						else {
							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							//CEP pesquisado não foi encontrado.
							alert("CEP não encontrado.");
						}
					});
		}
	</script>
</body>
</html>