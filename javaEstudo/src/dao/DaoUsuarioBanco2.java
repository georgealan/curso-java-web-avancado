package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDataBaseBanco2;
import entidades.Usuario;

public class DaoUsuarioBanco2 {

	private static Connection connection;
	
	/*Quando instanciarmos um objeto DaoUsuario automaticamente ele vai abrir a conexão com o banco de dados.*/
	public DaoUsuarioBanco2() {
		connection = ConnectionDataBaseBanco2.getConnection();
	}

	public List<Usuario> getUsuarios() throws Exception { /*Throws Exception lança uma exceção para cima*/
		List<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "select * from usuario ";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			
			Usuario usuario = new Usuario();
			usuario.setId(resultSet.getString("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));

			usuarios.add(usuario);
		}

		return usuarios;
	}

}
