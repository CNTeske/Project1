package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getRequestURI());
		switch (request.getRequestURI()) {
		case ("/Project1/fc/session"):
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getReader(), User.class);
			String username = user.getUsername();
			String password = user.getPassword();
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			request.getRequestDispatcher("/session").forward(request, response);
			break;
		case ("/Project1/fc/ers"):
			request.getRequestDispatcher("/ers").forward(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
