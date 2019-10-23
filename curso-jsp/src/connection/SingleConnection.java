package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Responsavel por fazer a conexão com o banco de dados.
 * É um padrão Singleton que vai gerar apenas uma conexão
 * e essa conexão vai ser distribuida por todo o sistema.
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

			if (connection == null) {/* Se a conexão for nula vai se conetar se não for nula vai continuar com a conexão que já existe.*/ 
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false); /*Evita que as alterações no banco de dados sejam feitas de forma altomatica, mas só quando a gente quiser que sejam feitas.*/
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
