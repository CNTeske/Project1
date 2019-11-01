package com.revature.daos;

public class TestClass {
public static void main(String[] args) {
	RequestDAO testObject = new RequestDAO();
	int testInt = testObject.getRole("ATanner", "testpass1");
	System.out.println(testInt);
}
}
