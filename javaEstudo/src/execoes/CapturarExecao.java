package execoes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pages/capturarExecao")
public class CapturarExecao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CapturarExecao() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String valor = request.getParameter("valorParam");
			
			Integer.parseInt(valor);
 
			response.setStatus(200);// ok nenhum erro
			response.getWriter().write("Processada com sucesso");
		} catch (Exception e) {
			response.setStatus(500);// erro interno do servidor 
			response.getWriter().write("Erro ao processar : " + e.getMessage()); // adicina valor ao responseText
		}

	}

}
