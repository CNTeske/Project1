package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.RequestDAO;
import com.revature.models.User;

public class TestServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void service(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {     
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        super.service(request, response);
        
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(request.getReader(), User.class);
		String username = user.getUsername();
		String password = user.getPassword();
		System.out.println(username);
		System.out.println(password);
		HttpSession session = request.getSession();
		RequestDAO dao = new RequestDAO();
		int role = dao.getRole(username, password);
		user.setRole(role);
		System.out.println(role);
		if(role==1||role==2) {
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("role", role);
		System.out.println(session.getAttribute("username"));
		System.out.println(session.getAttribute("password"));
		System.out.println(session.getAttribute("role"));
		response.setStatus(201);
		om.writeValue(response.getWriter(), user);
		} else {
			User badUser = new User("","", 0);
			om.writeValue(response.getWriter(), badUser);
		}
	}
}