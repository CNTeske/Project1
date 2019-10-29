package project.zero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Validate {
	static int isUserNamed(String name) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "select count(username) from Users where username = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			int result = 0;
			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	static int isAccountNamed(String name) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT COUNT(account_name) from Accounts where account_name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			int result = 0;
			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}
			if (result == 0) {
				return 0;
			} else {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	static int isUserAllowed(int userID, String accountname) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT COUNT(permit_id) from Permissions where user_id = ? AND "
					+ "account_id = (select account_id from Accounts where account_name = ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userID);
			statement.setString(2, accountname);
			ResultSet resultSet = statement.executeQuery();
			int result = 0;
			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	static int isUserAllowed(String username, String accountname) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT COUNT(permit_id) from Permissions where "
					+ "user_id = (select user_id from Users where username = ?) AND "
					+ "account_id = (select account_id from Accounts where account_name = ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, accountname);
			ResultSet resultSet = statement.executeQuery();
			int result = 0;
			while (resultSet.next()) {
				result = resultSet.getInt("count");
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
