/**
 * Classe responsavel pela manipulação do banco de dados, aqui a gente insere as queries do SQL para 
 * realizar as operações no banco de dados.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanTelefones;
import connection.SingleConnection;

public class DaoTelefones {
	// CRIAMOS UMA VARIAVEL DO TIPO CONNECTION PARA QUE POSSAMOS INSTANCIAR A NOSSA
	// CONEXÃO SINGLETON
	private Connection connection;

	// USAMOS UM MÉTODO PUBLICO PARA CAPTURAR A CONEXÃO SINGLETON.
	public DaoTelefones() {
		connection = SingleConnection.getConnection();
	}

	// MÉTODO PARA SALVAR UM PRODUTO NO BANCO DE DADOS.
	public void salvar(BeanTelefones telefone) {
		try {
			String sql = "insert into telefone (numero, tipo, usuario) values (?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanTelefones> listar(Long user) throws Exception {
		List<BeanTelefones> listar = new ArrayList<BeanTelefones>();

		String sql = "select * from telefone where usuario = " + user;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanTelefones telefone = new BeanTelefones();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));
			listar.add(telefone);
		}

		return listar;
	}

	public void delete(String id) {

		try {
			String sql = "delete from telefone where id = '" + id + "'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();

			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}


}
