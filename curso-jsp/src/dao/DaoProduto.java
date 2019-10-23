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

import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {
	// CRIAMOS UMA VARIAVEL DO TIPO CONNECTION PARA QUE POSSAMOS INSTANCIAR A NOSSA
	// CONEXÃO SINGLETON
	private Connection connection;

	// USAMOS UM MÉTODO PUBLICO PARA CAPTURAR A CONEXÃO SINGLETON.
	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	// MÉTODO PARA SALVAR UM PRODUTO NO BANCO DE DADOS.
	public void salvar(BeanProduto produto) {
		try {
			String sql = "insert into produto (nome, quantidade, valor) values (?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setDouble(2, produto.getQuantidade());
			insert.setDouble(3, produto.getValor());
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

	public List<BeanProduto> listar() throws Exception {
		List<BeanProduto> listar = new ArrayList<BeanProduto>();

		String sql = "select * from produto";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			listar.add(produto);
		}

		return listar;
	}

	public void delete(String id) {

		try {
			String sql = "delete from produto where id = '" + id + "'";
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

	public BeanProduto consultar(String id) throws Exception {
		String sql = "select * from produto where id='" + id + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			return produto;
		}

		return null;
	}

	public boolean validarNome(String nome) throws Exception {
		String sql = "select count(1) as qtd from produto where nome='" + nome + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;/* Return true */
		}

		return false;
	}

	public void atualizar(BeanProduto produto) {
		try {
			String sql = "update produto set nome = ?, quantidade = ?, valor = ?  where id = " + produto.getId();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, produto.getNome());
			preparedStatement.setDouble(2, produto.getQuantidade());
			preparedStatement.setDouble(3, produto.getValor());
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
