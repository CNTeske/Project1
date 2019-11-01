package com.revature.service;

import java.util.List;

import com.revature.daos.RequestDAO;
import com.revature.models.ERS_Request;

public class ReqService {
	RequestDAO dao = new RequestDAO();
	public ERS_Request save(ERS_Request req, String username, String passcode) {
		if (req.getResolver() == null) {
			req = dao.createRequest(username, passcode, req);
		} else {
			req = dao.modifyRequest(username, passcode, req);
		}
		return req;
	}
	public List<ERS_Request> view(String username, String passcode){
		List<ERS_Request> reqList = dao.viewRequest(username, passcode);
		return reqList;
	}

}
