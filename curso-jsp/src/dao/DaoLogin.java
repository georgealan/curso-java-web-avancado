package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class DaoLogin {
	private Connection connection;// Criando variavel privada da Classe Connection.

	public DaoLogin() {
		/**
		 * É um método para fazer referencia a conexão estabelecida pelo Singleton. Ou
		 * seja ele vai puxar a conexão que já está estabelecida com o banco de dados.
		 */
		connection = SingleConnection.getConnection();
	}

	/**
	 * @param login
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public boolean validarLogin(String login, String senha) throws Exception { // Subimos a exeção pois estamos fazendo
																				// uma consulta no banco e não gravando
																				// dados.
		String sql = "select * from usuario where login = '" + login + "' and senha = '" + senha + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) { // Vai percorrer todos os resultados do select, e se existir um resultado vai
								// retornar verdadeiro senão falso.
			return true; // Possui usuario.
		} else {
			return false; // Não validou usuario.
		}

		/**
		 * Este método é responsavel por validar a entrada de dados nos campos de login
		 * e senha, ele faz um select no banco de dados adicionando dinamicamente os
		 * parâmetros dos atributos do método.
		 */
	}
}
