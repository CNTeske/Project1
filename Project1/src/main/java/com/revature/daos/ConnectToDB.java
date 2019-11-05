package com.revature.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
	public static Connection getConnection(int role) {
		String url = "jdbc:postgresql://teskedatabase.cp4i4yrxj8az.us-east-1.rds.amazonaws.com:5432/postgres";
		try {
			if (role == 2) {
				return DriverManager.getConnection(url, System.getenv("P1managername"), System.getenv("P1managerpass"));
			} else {
				return DriverManager.getConnection(url, System.getenv("P1username"), System.getenv("P1userpass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database not accessable.");
			return null;
		}
	}
}
