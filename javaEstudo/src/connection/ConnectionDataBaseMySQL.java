package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Realizar a conex�o com o banco de dados
 * Conex�o Postgresql para o banco empresa
 * @author alex
 * 
 */
public class ConnectionDataBaseMySQL {

	private static String banco = "jdbc:mysql://localhost:3306/aprendendojsp?autoReconnect=true";
	private static String password = "admin";
	private static String user = "admin";
	private static Connection connection = null;
	
	/*Retorna a conex�o apenas uma vez, � um singleton*/
	static {
		conectar();
	}

	public ConnectionDataBaseMySQL() {
		conectar();
	}
	
	/*Este m�todo � privado pois somente est aclasse pode acess�-lo*/
	private static void conectar() {

		try {
			if (connection == null) {
					Class.forName("com.mysql.jdbc.Driver");
					connection = DriverManager.getConnection(banco, user, password);
			}
		} catch (Exception exception) {
			throw new RuntimeException("Erro ao conectar com o banco de dados"
					+ exception.getMessage());
		}

	}
	
	/**
	 * Retorna a conex�o do banco de dados 
	 * @return Connection SQL
	 */
	
	/*Este m�todo � o m�todo que vai poder ser acessado por outras classes, ele serve
	 * vou usar uma analogia, de ponte para ser acessado aqui.*/
	public static Connection getConnection(){
		return  connection;
	}
	

}
