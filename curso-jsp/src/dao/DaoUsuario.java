package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

/**
 * Nesta Classe DAO nós fazemos as manipulações no banco de dados, é nela que
 * escrevemos as instruções sql para fazer a manipulção do banco de dados.
 * 
 * @author George
 *
 */
public class DaoUsuario {
	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	// Método para salvar um novo usuario no banco.
	public void salvar(BeanCursoJsp usuario) {
		try {
			String sql = "insert into usuario(login, senha, nome, telefone, cep, rua, bairro, cidade, estado, "
					+ " ibge, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura, "
					+ " ativo, sexo, perfil) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getTelefone());
			insert.setString(5, usuario.getCep());
			insert.setString(6, usuario.getRua());
			insert.setString(7, usuario.getBairro());
			insert.setString(8, usuario.getCidade());
			insert.setString(9, usuario.getEstado());
			insert.setString(10, usuario.getIbge());
			insert.setString(11, usuario.getFotoBase64());
			insert.setString(12, usuario.getContentType());
			insert.setString(13, usuario.getCurriculoBase64());
			insert.setString(14, usuario.getContentTypeCurriculo());
			insert.setString(15, usuario.getFotoBase64Miniatura());
			insert.setBoolean(16, usuario.isAtivo());
			insert.setString(17, usuario.getSexo());
			insert.setString(18, usuario.getPerfil());
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
	
	/*Mesmo método com a diferença que este recebe um parametro.
	 * Usamos este método na servlet que foi criada especialmente para fazer pesquisa de usuario.
	 * */
	public List<BeanCursoJsp> listar (String descricaoconsulta) throws SQLException{
		String sql = "select * from usuario where login <> 'admin' and nome like '%"+descricaoconsulta+"%'";
		return consultarUsuarios(sql);
	}
	
	// Método responsável por listar todos os usuarios do sistema.
	public List<BeanCursoJsp> listar() throws Exception {
		String sql = "select * from usuario where login <> 'admin'";
		return consultarUsuarios(sql);
	}
	/*O método foi refatorado para que fosse possivel criar um método que recebe parametro para reutilizar 
	 * o mesmo método, agora eles são dinamicos. */
	private List<BeanCursoJsp> consultarUsuarios(String sql) throws SQLException {
		List<BeanCursoJsp> listar = new ArrayList<BeanCursoJsp>();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setTelefone(resultSet.getString("telefone"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setContentType(resultSet.getString("contenttype"));
			// beanCursoJsp.setFotoBase64(resultSet.getString("fotobase64"));
			beanCursoJsp.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			beanCursoJsp.setCurriculoBase64(resultSet.getString("curriculobase64"));
			beanCursoJsp.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			beanCursoJsp.setAtivo(resultSet.getBoolean("ativo"));
			beanCursoJsp.setSexo(resultSet.getString("sexo"));
			beanCursoJsp.setPerfil(resultSet.getString("perfil"));
			listar.add(beanCursoJsp);
		}
		
		return listar;
	}

	// Método para deletar um usuario da lista que foi puxada com o método acima.
	public void delete(String id) {
		try {
			String sql = "delete from usuario where id = '" + id + "' and login <> 'admin'";
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

	// Método para consultar um usuario da lista que foi puxada com o método acima.
	public BeanCursoJsp consultar(String id) throws Exception {
		String sql = "select * from usuario where id = '" + id + "' and login <> 'admin'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setTelefone(resultSet.getString("telefone"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setContentType(resultSet.getString("contenttype"));
			beanCursoJsp.setFotoBase64(resultSet.getString("fotobase64"));
			beanCursoJsp.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			beanCursoJsp.setCurriculoBase64(resultSet.getString("curriculobase64"));
			beanCursoJsp.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
			beanCursoJsp.setAtivo(resultSet.getBoolean("ativo"));
			beanCursoJsp.setSexo(resultSet.getString("sexo"));
			beanCursoJsp.setPerfil(resultSet.getString("perfil"));
			return beanCursoJsp;
		}

		return null;
	}

	// VALIDAÇÃO LOGIN: VALIDAR O LOGIN PARA SALVAR NO BANCO (INSERT)
	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; /* return true */
		}

		return false;
	}

	// VALIDAÇÃO LOGIN: VALIDAR A SENHA NO UPDATE DO BANCO (UPDATE)
	public boolean validarLoginUpdate(String login, String id) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "' and id <> " + id;

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; /* return true */
		}

		return false;
	}

	// VALIDAÇÃO SENHA: VALIDAR A SENHA PARA SALVAR NO BANCO. (INSERT)
	public boolean validarSenha(String senha) throws Exception {
		String sql = "select count(1) as total from usuario where senha = '" + senha + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("total") <= 0; /*
													 * return true, assim vai poder seguir com o cadastro, pois não
													 * existe um ou mais usuarios com uma senha igual
													 */
		}

		return false; /*
						 * Retorna falso e não vai concluir o cadastro pois tem um ou mais usuarios com
						 * a mesma senha.
						 */
	}

	// VALIDAÇÃO SENHA: VALIDAR SENHA NO UPDATE DOS DADOS. (UPDATE)
	public boolean validarSenhaUpdate(String senha, String id) throws Exception {
		String sql = "select count(1) as totalSU from usuario where senha = '" + senha + "' and id <> " + id;

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("totalSU") <= 0; /*
														 * return true, assim vai poder seguir com o cadastro, pois não
														 * existe um ou mais usuarios com uma senha igual
														 */
		}

		return false; /*
						 * Retorna falso e não vai concluir o cadastro pois tem um ou mais usuarios com
						 * a mesma senha.
						 */
	}

	/**
	 * Este método é respoonsavel por fazer o update no banco de dados.
	 * 
	 * @param usuario
	 */
	public void atualizar(BeanCursoJsp usuario) {
		try {

			StringBuilder sql = new StringBuilder();

			/*
			 * O sql.append funciona da mesma forma que a concatenação + do sql, ele vai
			 * juntando tudo.
			 * 
			 * Aqui abaixo estamos usando o sql dinamico para verificar e executar as
			 * queryes em determinadas condições.
			 */

			sql.append(" update usuario set login = ?, senha = ?, nome = ?, telefone = ?, ");
			sql.append(" cep = ?, rua = ?, bairro = ?, cidade = ?, ");
			sql.append(" estado = ?, ibge = ?, ativo = ?, sexo = ?, perfil = ?");

			if (usuario.isAtualizarImage()) {
				sql.append(", fotobase64 = ?, contenttype = ?");
			}

			if (usuario.isAtualizarPdf()) {
				sql.append(", curriculobase64 = ?, contenttypecurriculo = ? ");
			}

			if (usuario.isAtualizarImage()) {
				sql.append(", fotobase64miniatura = ? "); /*Sempre dar um espaço no inicio do sql para evitar problemas.*/
			}

			sql.append(" where id = " + usuario.getId());

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql.toString()); /* Usa-se o toString para o StringBuilder fatorar. */
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.setString(4, usuario.getTelefone());
			preparedStatement.setString(5, usuario.getCep());
			preparedStatement.setString(6, usuario.getRua());
			preparedStatement.setString(7, usuario.getBairro());
			preparedStatement.setString(8, usuario.getCidade());
			preparedStatement.setString(9, usuario.getEstado());
			preparedStatement.setString(10, usuario.getIbge());
			preparedStatement.setBoolean(11, usuario.isAtivo());
			preparedStatement.setString(12, usuario.getSexo());
			preparedStatement.setString(13, usuario.getPerfil());

			if (usuario.isAtualizarImage()) {
				preparedStatement.setString(14, usuario.getFotoBase64());
				preparedStatement.setString(15, usuario.getContentType());
			}

			if (usuario.isAtualizarPdf()) {

				if (usuario.isAtualizarPdf() && !usuario.isAtualizarImage()) {
					preparedStatement.setString(14, usuario.getCurriculoBase64());
					preparedStatement.setString(15, usuario.getContentTypeCurriculo());
				} else {
					preparedStatement.setString(16, usuario.getCurriculoBase64());
					preparedStatement.setString(17, usuario.getContentTypeCurriculo());
				}

			} else {
				if (usuario.isAtualizarImage()) {
					preparedStatement.setString(16, usuario.getFotoBase64Miniatura());
				}
			}

			if (usuario.isAtualizarImage() && usuario.isAtualizarPdf()) {
				preparedStatement.setString(18, usuario.getFotoBase64Miniatura());
			}

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
