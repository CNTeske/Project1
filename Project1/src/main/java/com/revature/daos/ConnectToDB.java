package com.revature.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
	public static Connection getConnection(int role) {
		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=ers";

		try {
			Connection conn = DriverManager.getConnection(url, System.getenv("Pirate_Login"), 
					System.getenv("Pirate_PW")); 
 
			return conn; 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database not accessable.");
			return null;
		}
	}
}
