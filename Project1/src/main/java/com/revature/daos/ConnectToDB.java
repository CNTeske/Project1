package com.revature.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectToDB {
	public static Connection getConnection(int role) {
		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=ers";

		try {
			Connection conn = DriverManager.getConnection(url, System.getenv("Pirate_Login"),
					System.getenv("Pirate_PW"));

			return conn;
//			if (role == 2) {
//				return DriverManager.getConnection(url, System.getenv("P1managername"), System.getenv("P1managerpass"));
//			} else {
//				return DriverManager.getConnection(url, System.getenv("P1username"), System.getenv("P1userpass"));
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database not accessable.");
			return null;
		}
	}
}
