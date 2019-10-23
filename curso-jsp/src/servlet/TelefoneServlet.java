package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanTelefones;
import dao.DaoTelefones;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefones daoTelefones = new DaoTelefones();

	public TelefoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			if (user != null) {

				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				if (acao.equalsIgnoreCase("addFone")) {
					request.getSession().setAttribute("userEscolhido", beanCursoJsp);
					request.setAttribute("userEscolhido", beanCursoJsp);
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefone", daoTelefones.listar(beanCursoJsp.getId()));
					view.forward(request, response);

				} else if (acao.equalsIgnoreCase("deleteFone")) {
					String foneId = request.getParameter("foneId");
					daoTelefones.delete(foneId);

					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("userEscolhido", beanCursoJsp);
					request.setAttribute("telefone", daoTelefones.listar(Long.parseLong(user)));
					request.setAttribute("msg", "Excluído Com Sucesso!");
					view.forward(request, response);
				}
			}else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Quando clicar em salvar vai recuperar o objeto que está na sessão, que é o
			// usuario.
			BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

			// Vai pegar o numero e o tipo do telefone.
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			String acao = request.getParameter("acao");

			if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

				if (numero == null || (numero != null && numero.isEmpty())) {

					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefone", daoTelefones.listar(beanCursoJsp.getId()));
					request.setAttribute("msg", "Informe o numero do telefone!");
					view.forward(request, response);

				} else {

					BeanTelefones beanTelefone = new BeanTelefones();
					beanTelefone.setNumero(numero);
					beanTelefone.setTipo(tipo);
					beanTelefone.setUsuario(beanCursoJsp.getId());
					daoTelefones.salvar(beanTelefone);
					request.getSession().setAttribute("userEscolhido", beanCursoJsp);
					request.setAttribute("userEscolhido", beanCursoJsp);

					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefone", daoTelefones.listar(beanCursoJsp.getId()));
					request.setAttribute("msg", "Salvo Com Sucesso!");
					view.forward(request, response);

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

}
