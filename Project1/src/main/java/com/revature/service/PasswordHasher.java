package com.revature.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.daos.ConnectToDB;

public class PasswordHasher {

	public static String getSecurePassword(String passwordToHash, String username) {
		byte[] b = gBytes(username);
		if (b != null) {
			String generatedPassword = null;
			try {
				// Create MessageDigest instance for MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				// Add password bytes to digest
				md.update(b);
				// Get the hash's bytes
				byte[] bytes = md.digest(passwordToHash.getBytes());
				// This bytes[] has bytes in decimal format;
				// Convert it to hexadecimal format
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < bytes.length; i++) {
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				// Get complete hashed password in hex format
				generatedPassword = sb.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return generatedPassword;
		}
		return "";
	}

	public static byte[] gBytes(String username) {
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select salt from ers_users where ers_username=?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				byte[] b = resultSet.getBytes("salt");
				return b;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}