package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ERS_Request;
import com.revature.service.ReqService;

public class ERSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
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
	ReqService reqService = new ReqService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String passcode = (String) session.getAttribute("passcode");
		ObjectMapper om = new ObjectMapper();
		List<ERS_Request> reqList = reqService.view(username, passcode);
		om.writeValue(response.getWriter(), reqList);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		
//		Cookie[] cookies = request.getCookies();
//		String username = "";
//		String password = "";
//		for(Cookie c:cookies) {
//			if(c.getName().equals("username")) username = c.getValue();
//			if(c.getName().equals("password")) password = c.getValue();
//		}
		
		
//		String username = (String) session.getAttribute("username");
//		String password = (String) session.getAttribute("password");
		
		String username = "ATanner";
		String password = "testpass1";
		ObjectMapper om = new ObjectMapper();
		ERS_Request req = om.readValue(request.getReader(), ERS_Request.class);
		req = reqService.save(req, username, password);
		om.writeValue(response.getWriter(), req);
		
	}

}
