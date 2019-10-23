package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDataBase;
import entidades.Projeto;
import entidades.Series;

public class DaoGanttChart {
	private Connection connection;

	public DaoGanttChart() {
		connection = ConnectionDataBase.getConnection();
	}
	
	public void atualizar(Series series)throws Exception {
		String sqlUpdate = "update series set datainicial = '"
				+ series.getDatainicial() + "' , datafinal = '"
				+ series.getDatafinal() + "' " + "where id = " + series.getId()
				+ " and projeto = " + series.getProjeto();
		connection.prepareStatement(sqlUpdate).executeUpdate();
	}

	public List<Projeto> getProjetos() throws Exception {
		List<Projeto> projetos = new ArrayList<Projeto>();

		String sqlProjetos = "select * from projeto";
		PreparedStatement statementProjeto = connection.prepareStatement(sqlProjetos);
		ResultSet resultSetProjetos = statementProjeto.executeQuery();
		
		/*Usamos um laço while para varrer a lista dos objetos Projeto.*/
		while (resultSetProjetos.next()) { //Enquanto existir Objetos Projeto sendo carregados na lista...
			Projeto projeto = new Projeto(); // para cada projeto encontrado criamos uma instancia do objeto Pojeto...
			projeto.setId(resultSetProjetos.getLong("id")); // Capturamos o id...
			projeto.setNome(resultSetProjetos.getString("nome")); //Capturamos o nome...
			
			/*Dentro do laço do Projeto fazemos outro laço, um laço dentro do outro, pois a tabela projeto
			 * possui um relacionamento de 1 para muitos com a tabela series, por isso para a gente capturar
			 * os dados da tabela series temos que fazer a consulta no mesmo laço.
			 * A query vai selecionar todos os resultados da tabela serie onde a coluna projeto tenha o 
			 * valor de id que foi capturado acima referente ao select da tabela projeto, com isso 
			 * pegamos os dados que fazem parte da ligação de cada usuario.*/
			String sqlSeries = "select * from series where projeto = " + resultSetProjetos.getLong("id");
			PreparedStatement preparedStatementSerie = connection.prepareStatement(sqlSeries);
			ResultSet resultSetSeries = preparedStatementSerie.executeQuery();
			
			/*Criamos uma nova lista do objeto Series*/
			List<Series> series = new ArrayList<Series>();
			
			/*Fazemos o mesmo em percorrer com um laço de repetição, mesmo que acima.*/
			while (resultSetSeries.next()) {
				Series serie = new Series();
				serie.setId(resultSetSeries.getLong("id"));
				serie.setNome(resultSetSeries.getString("nome"));
				serie.setProjeto(resultSetSeries.getLong("projeto"));
				serie.setDatainicial(resultSetSeries.getString("datainicial"));
				serie.setDatafinal(resultSetSeries.getString("datafinal"));
				/*Adicionamos os resultsets da lista series*/
				series.add(serie);
			}
			/*Aqui a gente seta na lista de projeto a lista de series, fazendo a referencia, unindo os dois.*/
			projeto.setSeries(series);
			
			/*E em seguida adicionamos o projeto.*/
			projetos.add(projeto);
		}
		/*Damos o retorno do projeto.*/
		return projetos;
	}

}
