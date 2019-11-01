package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.daos.RequestDAO;

public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDAO dao = new RequestDAO();
		int role = dao.getRole(username, password);
		if(role==1||role==2) {
		session.setAttribute("username", username);
		session.setAttribute("passcode", password);
		response.setStatus(200);
		}
	}
}
