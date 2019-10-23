package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserLogado;

@WebServlet("/pages/ServletAutenticacao")
public class ServletAutenticacao extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
    public ServletAutenticacao() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		// neste momento pode ser feito uma validação no banco de dados
		if (login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("123")){
			// se o login foi bem sucedido
			
			UserLogado userLogado = new UserLogado();
			userLogado.setLogin(login);
			userLogado.setSenha(senha);
			
			// adiciona usuário logado na sessão
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			session.setAttribute("usuario", userLogado);
			
			//redireciona para o sistema e autoriza
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    	dispatcher.forward(request, response);
			
		}else {// se o login falhou
			// redireciona para login novamente
			RequestDispatcher dispatcher = request.getRequestDispatcher("/autenticar.jsp");
	    	dispatcher.forward(request, response);
		}
		
	}

}
