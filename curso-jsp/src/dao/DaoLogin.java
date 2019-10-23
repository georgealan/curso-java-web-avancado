package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class DaoLogin {
	private Connection connection;// Criando variavel privada da Classe Connection.

	public DaoLogin() {
		/**
		 * � um m�todo para fazer referencia a conex�o estabelecida pelo Singleton. Ou
		 * seja ele vai puxar a conex�o que j� est� estabelecida com o banco de dados.
		 */
		connection = SingleConnection.getConnection();
	}

	/**
	 * @param login
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public boolean validarLogin(String login, String senha) throws Exception { // Subimos a exe��o pois estamos fazendo
																				// uma consulta no banco e n�o gravando
																				// dados.
		String sql = "select * from usuario where login = '" + login + "' and senha = '" + senha + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) { // Vai percorrer todos os resultados do select, e se existir um resultado vai
								// retornar verdadeiro sen�o falso.
			return true; // Possui usuario.
		} else {
			return false; // N�o validou usuario.
		}

		/**
		 * Este m�todo � responsavel por validar a entrada de dados nos campos de login
		 * e senha, ele faz um select no banco de dados adicionando dinamicamente os
		 * par�metros dos atributos do m�todo.
		 */
	}
}
