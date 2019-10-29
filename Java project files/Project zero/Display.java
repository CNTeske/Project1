package project.zero;

import java.util.List;

class Display {
	static void displayAccounts(int userID) {
		List <Accounts> accounts = GetAccounts.display(userID);
		System.out.println("------------------------------Accounts------------------------------");
		System.out.println("|    Name         |   Type   |  Cows | Sheep | Goats | Users");
		System.out.println("--------------------------------------------------------------------");
		for (Accounts i : accounts) {
			System.out.printf("| %15s | %8s | %5d | %5d | %5d | %s", i.getAccount_name(),
					i.getAccount_type(), i.getAmount_Cows(), i.getAmount_Sheep(), i.getAmount_Goats(), i.getUsers());
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------");
		System.out.print("Press 1 or say 'Open' to open a new pen.                  ");
		System.out.println("Press 2 or say 'Close' to close a pen.");
		System.out.print("Press 3 or say 'Add' to add a user to one of your pens.   ");
		System.out.println("Press 4 or say 'End' to close the current user account.");
		System.out.print("Press 5 or say 'Withdraw' to withdraw from a pen.         ");
		System.out.println("Press 6 or say 'Deposit'  to deposit into a pen.");
		System.out.print("Press 7 or say 'Transfer' to transfer between pens.       ");
		System.out.println("Press 8 or say 'Logout' to logout.");
		//Options: Create pen, Close pen, Add user, End service, Withdraw, Deposit, Transfer, and Logout.
	}
	static int getAction(){
		int actnum = ReadFromUser.readList("open", "close", "add", "end", "withdraw", "deposit", "transfer", "log");
		return actnum;
	}
}
