package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		ERS_Request req = om.readValue(request.getReader(), ERS_Request.class);
		req = reqService.save(req, req.getAuthor());
		om.writeValue(response.getWriter(), req);
		
	}

}
