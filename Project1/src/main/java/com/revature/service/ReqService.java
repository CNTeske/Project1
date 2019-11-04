package com.revature.service;

import java.util.List;

import com.revature.daos.RequestDAO;
import com.revature.models.ERS_Request;
import com.revature.models.User;

public class ReqService {
	RequestDAO dao = new RequestDAO();
	
	public ERS_Request save(ERS_Request req, int userid) {
		if (req.getResolver() == null) {
			req = dao.createRequest(userid, req);
		} else {
			req = dao.modifyRequest(userid, req);
		}
		return req;
		
	}
	public List<ERS_Request> view(User user){
		List<ERS_Request> reqList = dao.viewRequest(user);
		return reqList;
	}

}
