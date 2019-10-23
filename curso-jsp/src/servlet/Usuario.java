package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**==============================================================================
 * =	                      ANOTAÇÕES DE ESTUDO								=
 * ==============================================================================
 * 
 * byte[] é um array de bytes, uma de suas aplicações é para guardar objetos de tipo
 * files e imagens.
 * 
 * equalsIgnoreCase é para dar a instrução de ignorar letras em caixa alta e baixa.
 * 
 * O código abaixo irá percorrer todo o formulário para encontrar onde é feito o upload de
 * arquivo. Usamos uma lista para percorrer todo o formulário. Os arquivos vão
 * ser puxados quando ainda estiverem na area temporária do servidor, ou seja
 * ainda não foram gravados no banco de dados, buscamos eles antes de eles serem
 * gravados no banco. esse código abaixo só serveria se o fomulário fosse apenas
 * de upload de imagens, arquivos.
 * 
 *List FileItem fileItens = new ServletFileUpload(new
 * DiskFileItemFactory()).parseRequest(request);
 * 
 * for (FileItem fileItem : fileItens) { if
 * (fileItem.getFieldName().equals("foto")) {
 * 
 * String fotoBase64 = new Base64().encodeBase64String(fileItem.get()); String
 * contentType = fileItem.getContentType(); usuario.setFotoBase64(fotoBase64);
 * usuario.setContentType(contentType);
 * 
 * CAPTURA DE OBJETOS TEXTO, ARQUIVOS E IMAGENS
 * Para capturar objtos de imagem e files a gente usa o getInputStream(), getContentType(). 
 * E para arquivos de texto a gente usa o getParameter() 
 * 
 * 
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) /*
																					 * doGet requisita dados do
																					 * servidor.
																					 */
			throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			
			/*Ação de DELETAR*/
			if (acao != null && acao.equalsIgnoreCase("delete") && user != null) {
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			/*Ação de EDITAR*/	
			} else if (acao != null && acao.equalsIgnoreCase("editar")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);
			/*Ação de LISTAR TODOS OS USUARIOS*/	
			} else if (acao != null && acao
					.equalsIgnoreCase("Listartodos")) {/* Lista todos os usuarios do banco logo de inicio na tela. */

				RequestDispatcher view = request
						.getRequestDispatcher("/cadastroUsuario.jsp"); /*
																		 * O RequestDispatcher é usado para setar qual
																		 * tela quer direcionar a visualização.
																		 */
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

				/*
				 * Ação referente ao DOWNLOAD das imagens É ela que é chamada quando clicamos na
				 * imagem de thumbnail.
				 */
			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				BeanCursoJsp usuario = daoUsuario.consultar(user);
				if (usuario != null) {

					String contentType = "";
					byte[] fileBytes = null;
					String tipo = request.getParameter("tipo");

					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
					} else if (tipo.equalsIgnoreCase("curriculo")) {
						contentType = usuario.getContentTypeCurriculo();
						fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
					}

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					/* Coloca os bytes em um objeto de entrada para processar */
					InputStream is = new ByteArrayInputStream(fileBytes);

					/* Inicio da resposta para o navegador */
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					os.flush();
					os.close();
				}
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) /*
																					 * doPost envia dados para o
																					 * servidor.
																					 */
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");

			; // flag checked = on flag unchecked = null

			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);
			
			/*Condição para verificar se a checkbox está checked ou unchecked 
			 * Se o parametro não for nulo e tiver o valor igual a on o valor é true se não false. e com
			 */
			if (request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
				usuario.setAtivo(true);
			} else {
				usuario.setAtivo(false);
			}

			try {

				/* INICIO FILE UPLOAD DE IMAGENS E PDF */

				// Irá validar se o formulário é um formulário de upload.
				if (ServletFileUpload.isMultipartContent(request)) {

					/* Processa imagem */
					Part imagemFoto = request.getPart("foto"); // Referente ao campo foto do formulário.

					/*
					 * Apenas vai processar a imagem se tiver alguma imagem informada pelo usuario
					 * Instanciando o objeto base64 para processar ele. ele retorna o InputStream
					 * que vai servir para a gente converter ele para byte, para em seguida
					 * converter para string e enviar para o banco.
					 */
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {// Verifica se realmente tem
																							// imagem.

						/*
						 * Evitar que o Java execute o método mais de uma vez, evitando um
						 * sobrecarregamento no processamento. por isso inserimos o método de conversão
						 * para bytes em uma variavel byte[]
						 */
						String fotoBase64 = new Base64()
								.encodeBase64String(converteStremParabyte(imagemFoto.getInputStream()));

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());

						/* Inicio miniatura imagem */

						/* Transformar em um bufferedImage */
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

						/* Pega o tipo da imagem */
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						/* Cria imagem em miniatura */
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();

						/* Escrever imagem novamente */
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());

						usuario.setFotoBase64Miniatura(miniaturaBase64);
						/* Fim miniatura imagem. */

					} else {

						usuario.setAtualizarImage(false);
					}

					/* Processa pdf */
					Part curriculoPdf = request.getPart("curriculo");

					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStremParabyte(curriculoPdf.getInputStream()));

						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
					} else {
						usuario.setAtualizarPdf(false);
					}
				}

				/* FIM FILE UPLOAD DE IMAGENS E PDF */

				// VARIAVEIS RESPONSAVEIS POR GUARDAR A MSG E DAR O GATILHO PARA A INSERÇÃO DOS
				// DADOS NO BANCO.
				String msg = null;
				boolean podeInserir = true;

				// FAZ VALIDAÇÕES DE OBRIGATORIEDADE DE PREENCHIMENTO DE CAMPOS.
				if (login == null || login.isEmpty()) {
					msg = "Login deve ser informado";
					podeInserir = false;
				} else if (senha == null || senha.isEmpty()) {
					msg = "Senha deve ser informada";
					podeInserir = false;
				} else if (senha.length() < 8) {
					msg = "Senha deve conter no mínimo 8 caracteres.";
					podeInserir = false;
				} else if (nome == null || nome.isEmpty()) {
					msg = "Nome deve ser informado";
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
					request.setAttribute("msg", "Este Login Pertence a Um Usuário!");
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
					request.setAttribute("msg", "Esta Senha Pertence a Um Usuário!");
					podeInserir = false;
				}

				if (msg != null) {
					request.setAttribute("msg", msg);
				} else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)
						&& daoUsuario.validarSenha(senha) && podeInserir) {
					daoUsuario.salvar(usuario);
				}

				if (id != null && !id.isEmpty() && podeInserir) {
					if (!daoUsuario.validarLoginUpdate(login, id)) {
						request.setAttribute("msg", "Login já existe para outro usuário");
					} else {
						daoUsuario.atualizar(usuario);
					}
				}

				/**
				 * PARA MANTER OS DADOS JÁ DIGITADOS NA TELA APÓS DE TER ELES JÁEXISTENTES A
				 * TELA SÓ VAI SER LIMPA APÓS OS DADOS PASSAREM PELA VERIFICAÇÃO SE JÁ EXISTEM.
				 */
				if (!podeInserir) {
					request.setAttribute("user", usuario);
				}

				/**
				 * Usamos um Dispatcher para setar uma variavel de usuarios para ele imprimir a
				 * lista na tela.
				 */

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				// request.setAttribute("msg", "Salvo com Sucesso!");
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * MÉTODO RESPONSAVEL POR CONVERTER A ENTRADA DE FLUXO DE DADOS DA IMAGEM PARA
	 * BYTE[]
	 */
	private byte[] converteStremParabyte(InputStream imagem) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}
}
