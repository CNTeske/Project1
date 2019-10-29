package project.zero;

public class ProjectZero {

	public static void main(String[] args) {
	boolean active = true;
	while (active == true) {
		LogIn login = new LogIn();
		int userID = login.start();
		if (userID != 0){
			boolean loggedIn = true;
			while (loggedIn == true) {
				Display.displayAccounts(userID);
				int actnum = Display.getAction();
				loggedIn = Actions.run(actnum, userID);
			}
		} else {
			System.out.println("Application exited");
			active = false;
		}
	}
	System.out.println("Application closed.");
	}
}
