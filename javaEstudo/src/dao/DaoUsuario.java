package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDataBase;
import entidades.Usuario;

public class DaoUsuario {

	private static Connection connection;
	
	/*Quando instanciarmos um objeto DaoUsuario automaticamente ele vai abrir a conex�o com o banco de dados.*/
	public DaoUsuario() {
		connection = ConnectionDataBase.getConnection();
	}

	public List<Usuario> getUsuarios() throws Exception { /*Throws Exception lan�a uma exce��o para cima*/
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
