package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoCalculaDataFinal;

@WebServlet("/pages/calcularDataFinal")
public class CalcularDataFinal extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	DaoCalculaDataFinal calculaDataFinal = new DaoCalculaDataFinal();
	
    public CalcularDataFinal() {
        super();
     
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Definição de dias e horas - 1 dia é igual a 8 horas*/
		
		try {
			int horaDia = 8;
			Date dataCalculada = null;
			Double totalDeDias = 0.0;
			
			/*Pegando parametro de data que vem do formulário, do input com id = data*/
			String data = request.getParameter("data");
			/*Pegando parametro de tempo que vem do formulário, do input com id = tempo
			 * temos que converter ele para inteiro para fazer os calculos, pois ele
			 * veio de um campo de input do tipo text.*/
			int tempo = Integer.parseInt(request.getParameter("tempo"));
			
			if(tempo <= horaDia) { //Mesmo dia
				Date dateInformada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(dateInformada);
				calendar.add(Calendar.DATE, 1);
				
				dataCalculada = calendar.getTime();
				totalDeDias = 1.0;
				
			}else {
				/*Calculo do total de dias, fazemos um casting para converter para double.*/
				totalDeDias = (double) (tempo / horaDia);
				
				if(totalDeDias <= 1) {
					/*Se o total de dias for menor ou igual a 1, a data vai permanecer a mesma, no
					 * mesmo dia então damos um parse no data para transformar ela na data de saida.*/
					dataCalculada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
				}else {//Caso de mais que 1 dia
					
					/*Criamos um novo objeto para transformar a data que veio em uma string em um objeto de data.*/
					Date dateInformada = new SimpleDateFormat("dd/MM/yyyy").parse(data);
					/*Precisamos criar um objeto calendar para trabalhar com a instancia dele.*/
					Calendar calendar = Calendar.getInstance();
					
					/*setamos a data que foi informada, a data que veio por parametro.*/
					calendar.setTime(dateInformada);
					/*Setamos o que queremos adicionar, abaixo queremos adicionar a data, e o resultado
					 * do calculo do total de dias que foi calculado acima.*/
					calendar.add(Calendar.DATE, totalDeDias.intValue());
					
					/*Calculo da data sendo recebido o getTime retorna a data.*/
					dataCalculada = calendar.getTime();
				}
			}
			/*Inserindo valores no DAO*/
			calculaDataFinal.gravaDataFinal(new SimpleDateFormat("dd/MM/yyyy").format(dataCalculada));
			
			/*Jogando valores na tela da página datas.jsp*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/datas.jsp");
			
			/*Setando os valores que serão recebidos no campo de input com o value dataFinal, o que está
			 * usando expression language JSP, o .format vai formatar a data para o valor atual da variavel
			 * dataCalculada.
			 * Usamos o request.setAttribute com os parametros de value-que representa o atributo value do 
			 * input do form html, com a tag de expression language do JSP a gente seta que os dados 
			 * devem ser jogados e visualizados naquele campo, o segundo aparametro é a variavel ou saida
			 * do dado que deseja referenciar.*/
			request.setAttribute("dataFinal", new SimpleDateFormat("dd/MM/yyyy").format(dataCalculada));
			/*Setando o valor do campo de input com o valor do total de dias,*/
			request.setAttribute("dias", totalDeDias);
			/*Dispachando fazendo o direcionamento.*/
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
