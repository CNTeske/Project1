package project.zero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class GetAccounts {
	static List<Accounts> display(int userID) {
		try (Connection connection = ConnectToDB.getConnection()) {
			String sql = "SELECT * from Accounts WHERE account_id IN (SELECT account_id from Permissions WHERE user_id = ?);";
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setInt(1, userID);
			ResultSet resultSet1 = statement1.executeQuery();
			List <Accounts> result= new ArrayList<>();
			List <Integer> ids = new ArrayList<Integer>();
			while (resultSet1.next()) {
				Accounts account = extractAccount(resultSet1);
				result.add(account);
				Integer id = resultSet1.getInt("account_id");
				ids.add(id);
			}
			for (int i=0;i<result.size(); i++) {
			sql = "SELECT STRING_AGG(username, ', ') from Users WHERE user_id IN "
					+ "(SELECT user_id from Permissions WHERE account_id = ?);";
			PreparedStatement statement2 = connection.prepareStatement(sql);
			int j = ids.get(i);
			statement2.setInt(1, j);
			ResultSet resultSet2 = statement2.executeQuery();
			String users = "";
			while (resultSet2.next()) {
				Accounts uses = result.get(i);
				users = resultSet2.getString("string_agg");
				uses.setUsers(users);
			}
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	private static Accounts extractAccount(ResultSet resultSet) throws SQLException {
		int accountID = resultSet.getInt("account_id");
		String accountName = resultSet.getString("account_name");
		String accountType = resultSet.getString("account_type");
		int cows = resultSet.getInt("amount_cows");
		int sheep = resultSet.getInt("amount_sheep");
		int goats = resultSet.getInt("amount_goats");
		Accounts account = new Accounts(accountID, accountName, accountType, cows, sheep, goats);
		return account;
	}
	
}
