package br.com.filter;

import java.io.IOException;

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

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

/*Temos que mapear a classe com a annotation WebFilter*/
@WebFilter(urlPatterns = { "/*" })
public class FilterAutenticacao implements Filter {

	@Override
	public void destroy() {
	}

	@Override // Este método vai ser executado em todas as requisições.
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/* Aqui dentro vamos trabalhar toda a nossa autenticação. */

		/* Temos que capturar os objetos da requisição, e da seção da requisição, */
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

		String url = req.getServletPath();

		if (!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {
			/*
			 * Se a url for diferente de index.jsf e o usuarioLogado for igual a null vai
			 * direcionar para a pagina index.jsf.
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return; // Return vazio para parar a execução do Java.
		} else {
			/*
			 * Executa as ações do request e do response, deixar executar por ultimo e as
			 * validações acima
			 */
			chain.doFilter(request, response);
		}

	}

	@Override // Este método vai ser executado quando subir o servidor.
	public void init(FilterConfig filterConfig) throws ServletException {
		JPAUtil.getEntityManager();
	}
}
