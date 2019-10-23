package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Realizar a conexão com o banco de dados
 * Conexão Postgresql para o banco empresa
 * @author alex
 * 
 */
public class ConnectionDataBaseMySQL {

	private static String banco = "jdbc:mysql://localhost:3306/aprendendojsp?autoReconnect=true";
	private static String password = "admin";
	private static String user = "admin";
	private static Connection connection = null;
	
	/*Retorna a conexão apenas uma vez, é um singleton*/
	static {
		conectar();
	}

	public ConnectionDataBaseMySQL() {
		conectar();
	}
	
	/*Este método é privado pois somente est aclasse pode acessá-lo*/
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
	 * Retorna a conexão do banco de dados 
	 * @return Connection SQL
	 */
	
	/*Este método é o método que vai poder ser acessado por outras classes, ele serve
	 * vou usar uma analogia, de ponte para ser acessado aqui.*/
	public static Connection getConnection(){
		return  connection;
	}
	

}
