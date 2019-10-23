package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

/*
 * Essa classe é responsavel por fazer as operaçoes no banco de dados,
 * para isso usamos um package DAO para ele fazer as modificações no objeto Modelo (model)
 * no pacote model.
 * */

public class UserPosDAO {
	/*
	 *Com essa classe vamos criar um objeto para fazer manipulação no banco de dados usamos ela 
	 *instanciando as classes que estão no pacote model. 
	 * */
	private Connection connection; //Chamamo

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	//Método responsavel por salvar um novo usuario no banco de dados.
	public void salvar(Userposjava userposjava) {
		try {
			String sql = "insert into userposjava (nome, email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());
			insert.execute();
			connection.commit();// salva no banco.

		} catch (Exception e) {
			try {
				connection.rollback();// Se ocorrer algum erro de operação ele irá reverter a operação.
			} catch (Exception e1) {
				e1.printStackTrace(); //Imprime o erro na tela.
			}
			e.printStackTrace();
		}
	}
	
	//Método para salvar telefone no banco de dados, com referencia à um usuario pela Foreign key.
	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Método para listar os dados da tabela do banco.
	public List<Userposjava> listar() throws Exception {
		List<Userposjava> list = new ArrayList<Userposjava>();

		String sql = "select * from userposjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));

			list.add(userposjava);
		}

		return list;
	}

	// Método para buscar apenas um dado da tabela do banco.
	public Userposjava buscar(Long id) throws Exception {
		Userposjava retorno = new Userposjava();

		String sql = "select * from userposjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // Retorna apenas um ou nenhum
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
		}

		return retorno;
	}
	
	//Método para listar os dados capturando com INNER JOIN.
	public List<BeanUserFone> listaUserFone(Long idUser){
		
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String sql = "select nome , numero, email from telefoneuser as fone ";
		sql += "inner join userposjava as userp ";
		sql += "on fone.usuariopessoa = userp.id ";
		sql += "where userp.id = " + idUser;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();
				userFone.setEmail(resultSet.getString("email"));
				userFone.setNome(resultSet.getString("nome"));
				userFone.setNumero(resultSet.getString("numero"));
				
				beanUserFones.add(userFone);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanUserFones;
	}
	
	// Método para atualizar dado da tabela do banco.
	public void atualizar(Userposjava userposjava) {
		try {
			
			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	// Método para deletar um dado da tabela do banco.
	public void deletar(Long id) {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	//Este método serve para deletar dados de duas tabelas que tem ligação PRIMARY KEY e FOREIGN KEY.
	public void deleteFonesPorUser(Long idUser) {
		try {
			
			String sqlFone = "delete from telefoneuser where usuariopessoa =" + idUser;
			String sqlUser = "delete from userposjava where id =" + idUser;
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
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
