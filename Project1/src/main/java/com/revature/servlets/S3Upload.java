package com.revature.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class S3Upload
 */
public class S3Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		super.service(request, response);
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ObjectMapper om = new ObjectMapper();
		
		BasicAWSCredentials cred = new BasicAWSCredentials((System.getenv("AWS_ACCESS_KEY")), System.getenv("AWS_SECRET_KEY"));

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(cred))
	            .withRegion("us-east-2")
	            .build();

		InputStream inputStream = request.getInputStream();

		byte[] contents = IOUtils.toByteArray(inputStream);
		InputStream stream = new ByteArrayInputStream(contents);

		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(contents.length);
		meta.setContentType("image/png");
		
		String fileName = (LocalDateTime.now()).toString();
		
		s3Client.putObject(new PutObjectRequest("ers-tickets", fileName, stream, meta));
		
		URL url = s3Client.getUrl("ers-tickets", fileName);
		
//		response.getWriter().write(url.toString());
		om.writeValue(response.getWriter(), url);
		
		inputStream.close();
	}

}
