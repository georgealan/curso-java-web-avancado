package filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.ConnectionDataBase;
import connection.ConnectionDataBaseBanco2;
import user.UserLogado;

@WebFilter(urlPatterns={"/pages/*"})    
public class FilterAutenticacao implements Filter{ 
	
	private static Connection connection;
	private static Connection connectionBanco2;
 
	//faz alguma coisa quando a aplicação é derrubada
	@Override
	public void destroy() {
		
	}

	//intercepta todas as requisições
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String urlParaAutenticar = req.getServletPath();	
			// retorna null caso não esteja logado
		    UserLogado userLogado = (UserLogado) session.getAttribute("usuario");
		    
		    if (userLogado == null && !urlParaAutenticar.equalsIgnoreCase("/pages/ServletAutenticacao")){ // usuário não logado
		    	RequestDispatcher dispatcher = request.getRequestDispatcher("/autenticar.jsp?url="+urlParaAutenticar);
		    	dispatcher.forward(request, response);
		    	return;// para o processo para redirecionar
		    }
	 	
			// executa as ações do request e response
			chain.doFilter(request, response);
		
	}

	// executa alguma coisa quando a aplicação é iniciada
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		connection = ConnectionDataBase.getConnection(); //Chama a conexão com o banco de dados toda vez que a apliicação é iniciada.
		connectionBanco2 = ConnectionDataBaseBanco2.getConnection(); //Chamada de outra conexão com outro banco de dados.
	}

}
