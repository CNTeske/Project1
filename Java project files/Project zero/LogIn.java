package project.zero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class LogIn {
	int start() {
		while (true) {
			System.out.println("Welcome to Green Pasture Stables!");
			System.out.println("If you would like to log into an existing account, say 'Login', or press 1.");
			System.out.println("If you would like to start a new account, say 'Create', or press 2.");
			int log = ReadFromUser.readList("login", "create", "close");
			if (log != 2) {
				System.out.println("Please say your username and password.");
				int userID = ReadFromUser.readLogIn(log);
				if (userID != 0) {
					return userID;
				}
			} else {
				return 0;
			}
		}
	}

	int user(String username, String passcode) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT user_id from Users WHERE username = ? AND passcode = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, passcode);
			ResultSet resultSet = statement.executeQuery();
			int result = 0;
			while (resultSet.next()) {
				result = resultSet.getInt("user_id");
			}
			if (result == 0) {
				System.out.println("That name and/or password is incorrect. Please check your pronounciation");
				return 0;
			} else {
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
