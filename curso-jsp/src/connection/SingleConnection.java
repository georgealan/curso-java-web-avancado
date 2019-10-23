package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Responsavel por fazer a conex�o com o banco de dados.
 * � um padr�o Singleton que vai gerar apenas uma conex�o
 * e essa conex�o vai ser distribuida por todo o sistema.
 * 
 * @author George
 *
 */
public class SingleConnection {
	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;

	static {
		conectar();
	}

	public SingleConnection() {
		conectar();
	}

	private static void conectar() {
		try {

			if (connection == null) {/* Se a conex�o for nula vai se conetar se n�o for nula vai continuar com a conex�o que j� existe.*/ 
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false); /*Evita que as altera��es no banco de dados sejam feitas de forma altomatica, mas s� quando a gente quiser que sejam feitas.*/
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao conectar com o banco de dados.");
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
