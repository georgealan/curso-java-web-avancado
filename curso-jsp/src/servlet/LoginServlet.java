package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoLogin;

/**
 * Servlet é uma classe do java para trabalhar com desenvolvimento web apesar de
 * não ser desenvolvida especialmente para isso. Nessa classe, são feitas as
 * manipulações das requisições, dois membros importantes são o request(entrada
 * normalmente) e o response(saída).
 * 
 * @author George
 *
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoLogin daoLogin = new DaoLogin();

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

		try {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			// Validação do login no front-end para não subir requisição ao servidor a toa.
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				if (daoLogin.validarLogin(login, senha)) { // Acesso liberado.
					RequestDispatcher dispatcher = request.getRequestDispatcher("acessoliberado.jsp");
					dispatcher.forward(request, response);
				}else { // Acesso negado.
					RequestDispatcher dispatcher = request.getRequestDispatcher("acessonegado.jsp");
					dispatcher.forward(request, response);
				}
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
