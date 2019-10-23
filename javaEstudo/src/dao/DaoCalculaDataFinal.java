package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.ConnectionDataBase;

public class DaoCalculaDataFinal {
	private Connection connection;
	
	public DaoCalculaDataFinal() {
		connection = ConnectionDataBase.getConnection();
	}
	
	public void gravaDataFinal(String date) throws Exception{
		String sql = "insert into finalprojetos (datafinal) values (?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, date);
		preparedStatement.execute();
		
	}
		
}
