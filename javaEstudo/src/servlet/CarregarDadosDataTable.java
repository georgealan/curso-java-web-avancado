package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import dao.DaoUsuario;
import entidades.Usuario;

@WebServlet("/pages/carregarDadosDataTable")
public class CarregarDadosDataTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
       
    public CarregarDadosDataTable() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Usamos o Get pois vamos pegar dados do servidor para exibir na tela, estamos requisitando dados e temos
		 * que jogar por URL.*/
		try {	
			
			List<Usuario> usuarios = daoUsuario.getUsuarios();	
			
			if (!usuarios.isEmpty()) {
				
				String data = "";
				int totalUsuarios = usuarios.size(); /*Verifica o total de registros de usuarios no banco de dados.*/
				int index = 1; /*Variavel de controle de incremento*/
				
				
				for (Usuario usuario : usuarios) {
					/*Guardamos o JSON em uma variavel String para setar ela abaixo no json de forma organizada e separada.*/ 
					data +=  " ["+
					      "\""+usuario.getId()+"\", "+
					      "\""+usuario.getLogin()+"\""+
					    "]";
					
					/*A condição abaixo serve para inserir a virgula no final de cada iteração do laço, 
					 * pois o JSON precisa que feche a virgula no final de cada instrução. A instrução faz
					 * a leitura do tamanho do index que inicia com 1 verifica se ele é maior que o número de 
					 * registros de usuarios no banco de dados, e se for ele adiciona a string , no final da
					 * variavel String data e depois incrementa para que quando chegar ao numero de igualdade
					 * do index e totalUsuarios forem iguais a instrução pare.*/
					if (index < totalUsuarios){
						data += ",";
					}
					index++;/*Variavel de incremento.*/
				}
				
				/*Virgulas no JSON substitui por ponto.
				 * O usuarios.size serve para retornar o número de usuarios do banco de dados.
				 * O código abaixo é o cabeçalho do JSON e é padrão do JSON.
				 * */
					String json = "{"+
							  "\"draw\": 1,"+
							  "\"recordsTotal\": "+usuarios.size()+","+
							  "\"recordsFiltered\": "+usuarios.size()+","+
							  "\"data\": ["+data+"]"+ // fechamento do data
							"}";// fechamento do json
					 
					/*Resposta para o servidor*/
					 response.setStatus(200); // reposta completa OK
					 response.getWriter().write(json);// json de resposta (escreve a resposta Http)
			}
		 
		}catch (Exception e){
			e.printStackTrace();
			response.setStatus(500);
		}
	 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
