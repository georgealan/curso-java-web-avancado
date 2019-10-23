package posjavamavenhibernate;
/*Toda vez que uma conecção com o banco de dados é necessária esta Classe vai ser chamada 
 * e ela vai ler o arquivo persistence.xml e vai deixar instanciada a conecção com o banco de dados.*/

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	/*Esta classe tem que seguir o padrão singleton porque ela não pode ser lida mais de uma vez se 
	 * não vai ocorrer erros.*/
	
	public static EntityManagerFactory factory = null;
	
	static {
		init();
	}
	
	private static void init() {
		try {
			
			if(factory == null) {
				factory = Persistence.createEntityManagerFactory("pos-java-maven-hibernate");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager getEntityManager(){
		return factory.createEntityManager(); // Prove a parte de persistencia.
	}
	
	public static Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
}
