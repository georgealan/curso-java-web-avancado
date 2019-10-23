package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JPAUtil;

/*Esta classe serve para inserir todos os métods de query do banco de dados.*/

public class DaoGeneric<E> {
	/*Acima passamos como parametro do método um List que pode ter qualquer nome, por convenção foi colocado E de entidade, mas 
	 * poderia ser qualquer outro, isso quer dizer que a classe vai ser capaz de carregar qualquer tipo de objeto criado*/
	
	/*Métodos genericos servem para utilizar em qualquer classe, abaixo usamos esses tipos de métodos genericos 
	 * que vamos poder usar em qualquer classe.*/
	
	public void salvar(E entidade){ //Este método só serve para salvar.
		/*No método salvar setamos o parametro E entidade para salvar no banco de dados.*/
		EntityManager entityManager = JPAUtil.getEntityManager(); //Aqui estamos iniciando a conexão pela classe singleton.
		EntityTransaction entityTransaction = entityManager.getTransaction(); //Aqui abrimos uma transação.
		entityTransaction.begin(); //Abrimos a transação.
		
		entityManager.persist(entidade); // Criamos a persistenciano banco de dados.
		
		entityTransaction.commit(); //Salvamos os dados no banco, com o commit.
		entityManager.close(); //Fechamos a transação.
	}
	
	public E merge(E entidade){//Este método salva ou atualiza e retorna o objeto que foi salvo no banco de dados, ele vai retornar a entidade.
		/**/
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		/*O merge vai salvar os dados no banco de dados e em seguida vai retornar os dados.*/
		E retorno = entityManager.merge(entidade); // Este merge retorna uma entidade. 
		
		entityTransaction.commit();
		entityManager.close(); 
		
		return retorno;
	}
	
	
	public void delete(E entidade){ //Este método vai servir para deletar um dado do banco de dados.
		/*No método salvar setamos o parametro E entidade para salvar no banco de dados.*/
		EntityManager entityManager = JPAUtil.getEntityManager(); //Aqui estamos iniciando a conexão pela classe singleton.
		EntityTransaction entityTransaction = entityManager.getTransaction(); //Aqui abrimos uma transação.
		entityTransaction.begin(); //Abrimos a transação.
		
		entityManager.remove(entidade); // Com isso removemos um dado do banco de dados.
		
		entityTransaction.commit();
		entityManager.close(); 
	}
	
	public void deletePorId(E entidade){ //Este método vai servir para deletar um dado do banco de dados.
		/*No método salvar setamos o parametro E entidade para salvar no banco de dados.*/
		EntityManager entityManager = JPAUtil.getEntityManager(); //Aqui estamos iniciando a conexão pela classe singleton.
		EntityTransaction entityTransaction = entityManager.getTransaction(); //Aqui abrimos uma transação.
		entityTransaction.begin(); //Abrimos a transação.
		
		Object id = JPAUtil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id= " + id).executeUpdate();
		/*O .executeUpdate serve tanto para fazer o update quanto o delete.
		 * O entidade.getClass().getCanonicalName é usado pois precisamos capturar uma entidade generica, ou seja uma entidade
		 * aleatória no scopo da view. o getCanonicalName Retorna o nome canônico da classe subjacente conforme definido 
		 * pela Especificação da linguagem Java. Retorna null se a classe subjacente não tiver um nome canônico 
		 * (isto é, se for uma classe local ou anônima ou um array cujo tipo de componente não possui um nome canônico).*/
		
		entityTransaction.commit();
		entityManager.close(); 
	}
	
	public List<E> getListEntity(Class<E> entidade){
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		/*Consulta no banco de dados genérica que */
		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		entityTransaction.commit();
		entityManager.close(); 
		
		return retorno;
	}
	
}
