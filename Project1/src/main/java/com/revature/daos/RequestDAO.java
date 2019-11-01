package com.revature.daos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.revature.models.ERS_Request;

public class RequestDAO {

	public ERS_Request createRequest(String username, String passcode, ERS_Request newRequest) {
		 // Completed, not tested
		// Takes the generated request and adds it to the database.
		int role = getRole(username, passcode);
		int id = getID(username, passcode);
		if (role == 1 || role == 2) {
			try (Connection conn = ConnectToDB.getConnection(role)) {
				String sql = "insert into ERS_Reimbursement (reimb_amount, reimb_description, "
						+ "reimb_author, reimb_type) values (?, ?, ?, ?) returning *;";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setBigDecimal(1, newRequest.getAmount());
				statement.setString(2, newRequest.getDescription());
				statement.setInt(3, id);
				statement.setInt(4, newRequest.getType());
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					newRequest = unpack(resultSet);
				}
				return newRequest;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ERS_Request modifyRequest(String username, String passcode, ERS_Request oldRequest) {
		 // Completed, not tested
		// Verifies user has permission, updates request in the database.
		int role = getRole(username, passcode);
		if (role == 2) {
			try (Connection conn = ConnectToDB.getConnection(role)) {
				int id = getID(username, passcode);
				String sql = "update ers_reimbursement set reimb_resolved = current_timestamp, "
						+ "reimb_resolver = ?, reimb_status = ? where reimb_id = ? returning *;";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, oldRequest.getResolver());
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
		}
		return null;
	}

	public List<ERS_Request> viewRequest(String username, String passcode) { // Completed, not tested
		// Gets the user role, and runs the appropriate select.
		int role = getRole(username, passcode);
		try (Connection conn = ConnectToDB.getConnection(role)) {
			String sql = "select ers_user_id from ers_users where ers_username = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				role = resultSet.getInt("ers_user_id");
			}
			if (role == 2) {
				sql = "select * from ers_reimbursement";
				PreparedStatement statement1 = conn.prepareStatement(sql);
				ResultSet resultSet1 = statement1.executeQuery();
				List<ERS_Request> Requests = new ArrayList<>();
				while (resultSet1.next()) {
					ERS_Request request = unpack(resultSet1);
					Requests.add(request);
				}
				return Requests;
			} else if (role == 1) {

				sql = "select * from ers_reimbursement where reimb_author = ?;";
				PreparedStatement statement1 = conn.prepareStatement(sql);
				ResultSet resultSet1 = statement1.executeQuery();
				List<ERS_Request> Requests = new ArrayList<>();
				while (resultSet1.next()) {
					ERS_Request request = unpack(resultSet1);
					Requests.add(request);
				}
				return Requests;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getRole(String username, String passcode) { // Completed, tested
		// Authorizes the above commands by determining the user's role.
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select user_role_id from ers_users where ers_username = ? and ers_password = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, passcode);
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

	public int getID(String username, String passcode) { // Completed, tested
		// Authorizes the above commands by determining the user's role.
		try (Connection conn = ConnectToDB.getConnection(1)) {
			String sql = "select user_id from ers_users where ers_username = ? and ers_password = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, passcode);
			ResultSet resultSet = statement.executeQuery();
			int id = 0;
			while (resultSet.next()) {
				id = resultSet.getInt("user_id");
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	private ERS_Request unpack(ResultSet resultSet) { // Completed, not tested
		try {
			int id = resultSet.getInt("reimb_id");
			String amountString = resultSet.getString("reimb_amount");
			amountString = amountString.substring(1).replace(",", "");
			BigDecimal amount = new BigDecimal("");
			Timestamp submitted = resultSet.getTimestamp("reimb_submitted");
			Timestamp resolved = resultSet.getTimestamp("reimb_resolved");
			String description = resultSet.getString("reimb_description");
			byte[] receiptArray = resultSet.getBytes("reimb_receipt");
			BufferedImage receipt = ImageIO.read(new ByteArrayInputStream(receiptArray));
			int author = resultSet.getInt("reimb_author");
			Integer resolver = resultSet.getInt("reimb_resolver");
			int status = resultSet.getInt("reimb_status");
			int type = resultSet.getInt("reimb_type");

			ERS_Request request = new ERS_Request(id, amount, submitted, resolved, description, receipt, author,
					resolver, status, type);
			return request;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
