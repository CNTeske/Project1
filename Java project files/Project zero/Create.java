package project.zero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Create {
	int user(String username, String passcode) {
		if (Validate.isUserNamed(username) == 0 && Validate.isAccountNamed(username) == 0) {
			try (Connection connection = ConnectToDB.getConnection()) {
				String sql = "INSERT into Users(username, passcode) VALUES (?, ?);";
				PreparedStatement statement1 = connection.prepareStatement(sql);
				statement1.setString(1, username);
				statement1.setString(2, passcode);
				statement1.executeUpdate();
				sql = "INSERT into Accounts(account_name, account_type) VALUES (?, 'Checking');";
				PreparedStatement statement2 = connection.prepareStatement(sql);
				statement2.setString(1, username);
				statement2.executeUpdate();
				sql = "SELECT user_id from Users WHERE username = ?;";
				PreparedStatement statement3 = connection.prepareStatement(sql);
				statement3.setString(1, username);
				ResultSet resultSet = statement3.executeQuery();
				int userID = 0;
				while (resultSet.next()) {
					userID = resultSet.getInt("user_id");
				}
				sql = "INSERT into Permissions(user_id, account_id) VALUES (?, "
						+ "(SELECT account_id from Accounts WHERE account_name = ?));";
				PreparedStatement statement4 = connection.prepareStatement(sql);
				statement4.setInt(1, userID);
				statement4.setString(2, username);
				statement4.executeUpdate();
				return userID;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			System.out.println("That name has already been claimed. Please say a different name.");
			return 0;
		}
	}

	static int account(String accountname, String accounttype, int userID) {
		if (Validate.isAccountNamed(accountname) == 0) {
			try (Connection connection = ConnectToDB.getConnection()) {
				String sql = "INSERT into Accounts(account_name, account_type) VALUES (?, ?);";
				PreparedStatement statement1 = connection.prepareStatement(sql);
				statement1.setString(1, accountname);
				statement1.setString(2, accounttype);
				statement1.executeUpdate();
				sql = "SELECT account_id from Accounts WHERE account_name = ?;";
				PreparedStatement statement3 = connection.prepareStatement(sql);
				statement3.setString(1, accountname);
				ResultSet resultSet3 = statement3.executeQuery();
				int accountID = 0;
				while (resultSet3.next()) {
					accountID = resultSet3.getInt("account_id");
				}
				sql = "INSERT into Permissions(user_id, account_id) VALUES (?, ?);";
				PreparedStatement statement4 = connection.prepareStatement(sql);
				statement4.setInt(1, userID);
				statement4.setInt(2, accountID);
				statement4.executeUpdate();
				return accountID;
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			System.out.println("That name has already been claimed. Please say a different name.");
			return 0;
		}
	}

	static int permit(String username, String accountName) {
		int test = Validate.isUserNamed(username);
		if (test == 1) {
			int test2 = Validate.isUserAllowed(username, accountName);
			if (test2 == 0) {
				try (Connection connection = ConnectToDB.getConnection()) {
					String sql = "INSERT into Permissions(user_id, account_id) VALUES "
							+ "((SELECT user_id from users WHERE username = ?), "
							+ "(SELECT account_id from Accounts WHERE account_name = ?));";
					PreparedStatement statement2 = connection.prepareStatement(sql);
					statement2.setString(1, username);
					statement2.setString(2, accountName);
					statement2.executeUpdate();
					return 2;
				} catch (SQLException e) {
					e.printStackTrace();
					return 0;
				}
			} else {
				System.out.println("That user already has access to that pen.");
				return 2;
			}
		} else {
			System.out.println("We have no user by that name. Please say a different name.");
			return 1;
		}
	}
}
