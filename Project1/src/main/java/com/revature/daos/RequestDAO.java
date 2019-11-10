package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import com.revature.models.ERS_Request;
import com.revature.models.User;

public class RequestDAO {

	public ERS_Request createRequest(String username, ERS_Request newRequest) {
		int id = getID(username);
			try (Connection conn = ConnectToDB.getConnection(1)) {
				String sql = "insert into ERS_Reimbursement (reimb_amount, reimb_description, "
						+ "reimb_author, reimb_type, reimb_receipt) values (?, ?, ?, ?, ?) returning *;";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setDouble(1, newRequest.getAmount());
				statement.setString(2, newRequest.getDescription());
				statement.setInt(3, id);
				statement.setInt(4, newRequest.getType());
				statement.setString(5, newRequest.getReceipt());
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					newRequest = unpack(resultSet);
				}
				return newRequest;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return null;
	}

	public ERS_Request modifyRequest(String username, ERS_Request oldRequest) {
		int resolverID = 0;
		int id = oldRequest.getId();
		try (Connection conn = ConnectToDB.getConnection(2)) {
			String sql = "select ers_user_id from ers_users where ers_username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, oldRequest.getResolver());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resolverID = resultSet.getInt("ers_user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
			try (Connection conn = ConnectToDB.getConnection(2)) {
				String sql = "update ers_reimbursement set reimb_resolved = current_timestamp, "
						+ "reimb_resolver = ?, reimb_status = ? where reimb_id = ? returning *;";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, resolverID);
				statement.setInt(2, oldRequest.getStatus());
				statement.setInt(3, id);
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					oldRequest = unpack(resultSet);
				}
				return oldRequest;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	public List<ERS_Request> viewRequest(User user) {
		int role = user.getRole();
		int authorid = getID(user.getUsername());
		try (Connection conn = ConnectToDB.getConnection(role)) {
			if (role == 2) {
				String sql = "select * from ers_reimbursement order by reimb_submitted DESC;";
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				List<ERS_Request> Requests = new ArrayList<>();
				while (resultSet.next()) {
					ERS_Request request = unpack(resultSet);
					Requests.add(request);
				}
				return Requests;
			} else if (role == 1) {
				String sql = "select * from ers_reimbursement where reimb_author = ? order by reimb_submitted DESC;";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, authorid);
				ResultSet resultSet = statement.executeQuery();
				List<ERS_Request> Requests = new ArrayList<>();
				while (resultSet.next()) {
					ERS_Request request = unpack(resultSet);
					Requests.add(request);
				}
				return Requests;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getRole(String username) {
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select user_role_id from ers_users where ers_username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			int role = 0;
			while (resultSet.next()) {
				role = resultSet.getInt("user_role_id");
			}
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int getVerifiedRole(String username, String password) {
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select user_role_id from ers_users where ers_username = ? and ers_password = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			int role = 0;
			while (resultSet.next()) {
				role = resultSet.getInt("user_role_id");
			}
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getID(String username) { // Completed, tested
		// Authorizes the above commands by determining the user's role.
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select ers_user_id from ers_users where ers_username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			int id = 0;
			while (resultSet.next()) {
				id = resultSet.getInt("ers_user_id");
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public String getName(Integer id) { // Completed, tested
		// Authorizes the above commands by determining the user's role.
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select ers_username from ers_users where ers_user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			String name = "";
			while (resultSet.next()) {
				name = resultSet.getString("ers_username");
			}
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	private ERS_Request unpack(ResultSet resultSet) { // Completed, not tested
		int id = 0; double amount=0;Timestamp submitted =null;Timestamp resolved =null;
		String description = ""; Integer authorid=0; Integer resolverid = 0; int status = 0; int type = 0; String receipt = "";
		try {
			id = resultSet.getInt("reimb_id");
			amount = resultSet.getDouble("reimb_amount");
			submitted = resultSet.getTimestamp("reimb_submitted");
			resolved = resultSet.getTimestamp("reimb_resolved");
			receipt = resultSet.getString("reimb_receipt");
			description = resultSet.getString("reimb_description");
			authorid = resultSet.getInt("reimb_author");
			resolverid = resultSet.getInt("reimb_resolver");
			status = resultSet.getInt("reimb_status");
			type = resultSet.getInt("reimb_type");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String author = getName(authorid);
		String resolver = getName(resolverid);
		ERS_Request request = new ERS_Request(id, amount, submitted, resolved, description, receipt, author, resolver,
				status, type);
		return request;
	}
	
}
