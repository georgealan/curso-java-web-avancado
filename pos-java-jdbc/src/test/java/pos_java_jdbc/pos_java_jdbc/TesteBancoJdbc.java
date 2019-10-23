package pos_java_jdbc.pos_java_jdbc;

import java.util.List;

import org.junit.Test;
import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

/*
 * Arquivo para teste dos métodos, ver se estão todos funcionando ok.
 * */
public class TesteBancoJdbc {

	//Teste de inserção de dados no banco
	@Test
	public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();

		userposjava.setNome("Marcia");
		userposjava.setEmail("marcia@gmail.com");

		userPosDAO.salvar(userposjava);
	}

	//Teste de listagem de todos os dados
	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			List<Userposjava> list = dao.listar();

			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("-------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Teste de busca de um unico dado
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			Userposjava userposjava = dao.buscar(15L);
			System.out.println(userposjava);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Teste de atualização de dados
	@Test
	public void initAtualizar() {
		try {
			UserPosDAO dao = new UserPosDAO();
			Userposjava objetoBanco = dao.buscar(15L);
			objetoBanco.setNome("Nome mudado com método atualizar");
			dao.atualizar(objetoBanco);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Teste de deletar dado da tabela do banco
	@Test
	public void initDeletar() {
		try {
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(15L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Teste para inserção de telefone no banco
	@Test
	public void testeInserteTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(11) 99875321");
		telefone.setTipo("Celular");
		telefone.setUsuario(17L);
		
		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);
	}
	
	//Teste para visualizar a lista do INNER JOIN
	@Test
	public void testeCarregarFonesUser() {
		
		UserPosDAO dao = new UserPosDAO();
		
		List<BeanUserFone> beanUserFones = dao.listaUserFone(17L);
		
		for(BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("------------------------------------------------------");
		}
	}
	
	/*Teste para deletar dados com ligação de PRIMARY KEY E FOREIGN KEY corretamente, sem 
	 * que ocorra o erro de não conseguir deletar o Pai antes do Filho.
	 */
	@Test
	public void testeDeleteUserFone() {
	
		UserPosDAO dao = new UserPosDAO(); //Instanciando o Objeto UserPosDAO
		dao.deleteFonesPorUser(16L);
		
	}
}
