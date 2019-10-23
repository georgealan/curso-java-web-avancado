package conexaojdbc;

/*
 * Classe responsavel por criar os métodos de conecção ao banco de dados ela usa o 
 *padrão Singleton para cria apenas uma conecção.
 * */

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection { //Vamos usar isso para criar uma conecção com o banco de dados do tipo Singleton.
	/*
	 * Singleton é para instanciar uma conexão apenas uma vez, ser usada uma unica vez.
	 * */
	private static String url = "jdbc:postgresql://localhost:5432/pos-java-jdbc";
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

			if (connection == null) {//Se a conexão estiver vazia, vai entrar neste método
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);//Serve para evitar fazer alterações com a conexão, sem a permissão.
				System.out.println("Conectou com Sucesso!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Método que usaremos em classes fora para conectar no banco de dados.
	 * */
	public static Connection getConnection() {
		return connection;
	}

}
