package project.zero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Amount {

	static int withdraw(String accountname, int amountCows, int amountSheep, int amountGoats) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT amount_Cows, amount_Sheep, amount_goats from Accounts WHERE account_name = ?;";
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setString(1, accountname);
			ResultSet resultSet = statement1.executeQuery();
			int[] amounts = new int[3];
			while (resultSet.next()) {
				amounts[0] = resultSet.getInt("amount_Cows");
				amounts[1] = resultSet.getInt("amount_Sheep");
				amounts[2] = resultSet.getInt("amount_Goats");
			}
			if (amounts[0] >= amountCows && amounts[1] >= amountSheep && amounts[2] >= amountGoats) {
				sql = "UPDATE Accounts set amount_Cows = ?, amount_Sheep = ?, amount_goats = ? WHERE account_name = ?;";
				PreparedStatement statement2 = connection.prepareStatement(sql);
				statement2.setInt(1, amounts[0] - amountCows);
				statement2.setInt(2, amounts[1] - amountSheep);
				statement2.setInt(3, amounts[2] - amountGoats);
				statement2.setString(4, accountname);
				statement2.executeUpdate();
				return 1;
			} else {
				String animals = "";
				if (amounts[0] < amountCows) {
					animals = animals.concat(", or cows");
				}
				if (amounts[1] < amountSheep) {
					animals = animals.concat(", or sheep");
				}
				if (amounts[2] < amountGoats) {
					animals = animals.concat(", or goats");
				}
				animals = animals.replaceFirst(", or ", "");
				System.out.println("You don't have enough " + animals + " in that pen.");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	static int withdraw(String accountname, int animal, int amount) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "";
			String sql2 = "";
			switch (animal) {
			case 0:
				sql = "SELECT amount_cows from Accounts WHERE account_name = ?;";
				sql2 = "UPDATE Accounts set amount_cows = ? WHERE account_name = ?;";
				break;
			case 1:
				sql = "SELECT amount_sheep from Accounts WHERE account_name = ?;";
				sql2 = "UPDATE Accounts set amount_sheep = ? WHERE account_name = ?;";
				break;
			case 2:
				sql = "SELECT amount_goats from Accounts WHERE account_name = ?;";
				sql2 = "UPDATE Accounts set amount_goats = ? WHERE account_name = ?;";
				break;
			}
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setString(1, accountname);
			ResultSet resultSet = statement1.executeQuery();
			int amountAvailable = 0;
			while (resultSet.next()) {
				amountAvailable = resultSet.getInt(1);
			}
			if (amountAvailable >= amount) {

				PreparedStatement statement2 = connection.prepareStatement(sql2);
				statement2.setInt(1, amountAvailable - amount);
				statement2.setString(2, accountname);
				statement2.executeUpdate();
				return 1;
			} else {
				System.out.println("You don't have enough of that animal in that pen.");
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	static int deposit(String accountname, int amountCows, int amountSheep, int amountGoats) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT amount_Cows, amount_Sheep, amount_goats from Accounts WHERE account_name = ?;";
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setString(1, accountname);
			ResultSet resultSet = statement1.executeQuery();
			int[] amounts = new int[3];
			while (resultSet.next()) {
				amounts[0] = resultSet.getInt("amount_Cows");
				amounts[1] = resultSet.getInt("amount_Sheep");
				amounts[2] = resultSet.getInt("amount_Goats");
			}
			sql = "UPDATE Accounts set amount_Cows = ?, amount_Sheep = ?, amount_goats = ? WHERE account_name = ?;";
			PreparedStatement statement2 = connection.prepareStatement(sql);
			statement2.setInt(1, amountCows + amounts[0]);
			statement2.setInt(2, amountSheep + amounts[1]);
			statement2.setInt(3, amountGoats + amounts[2]);
			statement2.setString(4, accountname);
			statement2.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	static int deposit(String accountname, int animal, int amount) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "";
			String sql2 = "";
			switch (animal) {
			case 0:
				sql = "SELECT amount_cows from Accounts WHERE account_name = ?;";
				sql2 = "UPDATE Accounts set amount_cows = ? WHERE account_name = ?;";
				break;
			case 1:
				sql = "SELECT amount_sheep from Accounts WHERE account_name = ?;";
				sql2 = "UPDATE Accounts set amount_sheep = ? WHERE account_name = ?;";
				break;
			case 2:
				sql = "SELECT amount_goats from Accounts WHERE account_name = ?;";
				sql2 = "UPDATE Accounts set amount_goats = ? WHERE account_name = ?;";
				break;
			}
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setString(1, accountname);
			ResultSet resultSet = statement1.executeQuery();
			int amountAvailable = 0;
			while (resultSet.next()) {
				amountAvailable = resultSet.getInt(1);
			}
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setInt(1, amountAvailable + amount);
			statement2.setString(2, accountname);
			statement2.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	static int transfer(String sendname, int amountCows, int amountSheep, int amountGoats, String recievename) {
		int check1 = withdraw(sendname, amountCows, amountSheep, amountGoats);
		if (check1 == 1) {
			check1 = deposit(recievename, amountCows, amountSheep, amountGoats);
		}
		return check1;
	}

	static int transfer(String sendname, int animal, int amount, String recievename) {
		int check1 = withdraw(sendname, animal, amount);
		if (check1 == 1) {
			check1 = deposit(recievename, animal, amount);
		}
		return check1;
	}
}
