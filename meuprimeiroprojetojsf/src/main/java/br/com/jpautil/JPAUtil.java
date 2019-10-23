package br.com.jpautil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory factory = null;
	/*Classe responsavel pela conexão unica, é um padrão singleton que permite apenas 
	 * uma conexão no sistema.*/
	
	static { //Unica conexão.
		if (factory == null) { //Se for vazia vai criar a conexão, que vai ser unica devido ao static.
			factory = Persistence.createEntityManagerFactory("meuprimeiroprojetojsf");
		}
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public static Object getPrimaryKey(Object entity) { //Este método serve para retornar a chave primaria do objeto.
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
		/*O getPersistenceUnitUtil abre acesso aos métodos de persistencia unit, de modo que
		 * que podemos usar o getIdentifier para capturar o Id da entidade.*/
	}
	
}
