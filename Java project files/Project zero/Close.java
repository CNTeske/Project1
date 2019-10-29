package project.zero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Close {
	static void user(int userID) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "DELETE from Permissions WHERE user_id = ?;";
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setInt(1, userID);
			statement1.executeUpdate();
			sql = "DELETE from Accounts WHERE account_id NOT IN (SELECT account_id from Permissions);";
			PreparedStatement statement2 = connection.prepareStatement(sql);
			statement2.executeUpdate();
			sql = "DELETE from Users WHERE user_id = ?;";
			PreparedStatement statement3 = connection.prepareStatement(sql);
			statement3.setInt(1, userID);
			statement3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	static void account(String accountName) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "DELETE from Permissions WHERE account_id = (SELECT account_id from Accounts WHERE account_name = ?);";
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setString(1, accountName);
			statement1.executeUpdate();
			sql = "DELETE from Accounts where account_name = ?;";
			PreparedStatement statement2 = connection.prepareStatement(sql);
			statement2.setString(1, accountName);
			statement2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
}
