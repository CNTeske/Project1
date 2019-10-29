package project.one;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
class ConnectToDB {
	public static Connection getConnection() {
		String url = "jdbc:postgresql://teskedatabase.cp4i4yrxj8az.us-east-1.rds.amazonaws.com:5432/postgres";
		try {
			return DriverManager.getConnection(url, System.getenv("DB_LOGIN"), System.getenv("DB_AWSPASS"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database not accessable.");
			return null;
		}
	}
		
}


