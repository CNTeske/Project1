package project.zero;

class Actions {
	static boolean run(int actnum, int userID) {
		boolean loop = true;
		String penName = "";
		String userName = "";
		String sendname = "";
		String recievename = "";
		int check = 0;
		switch (actnum) {
		case 0:
			while (loop == true) {
				penName = ReadFromUser.readAccount(userID, 0);
				if (penName.contentEquals("")) {
					return true;
				} else if (penName.equals("error")) {
					System.out.println("That name has already been claimed. Please say a different name.");
				} else {
					loop = false;
				}
			}
			System.out.println("Enter the type of the pen to create: Checking or Saving.");
			int type = ReadFromUser.readList("sav", "check", "exit");
			if (type == 3) {
				return true;
			} else if (type == 1) {
				Create.account(penName, "Checking", userID);
				System.out.println(penName + " has been successfully opened.");
			} else {
				Create.account(penName, "Saving", userID);
				System.out.println(penName + " has been successfully opened.");
			}
			return true;
		case 1:
			while (loop == true) {
				penName = ReadFromUser.readAccount(userID, 1);
				if (penName.contentEquals("")) {
					loop = false;
				} else if (penName.contentEquals("error")) {
					System.out.println("You don't have a pen by that name.");
				} else {
					Close.account(penName);
					System.out.println(penName + " has been successfully closed.");
					loop = false;
				}
			}
			return true;
		case 2:
			while (loop == true) {
				userName = ReadFromUser.readUser();
				if (userName.contentEquals("")) {
					loop = false;
				} else if (!userName.contentEquals("error")) {
					penName = ReadFromUser.readAccount(userID, 1);
					if (penName.contentEquals("")) {
						loop = false;
					} else if (penName.equals("error")) {
						System.out.println("You don't have a pen by that name. Please say a different name.");
					} else {
						if (Validate.isUserAllowed(userName, penName) == 1) {
							System.out.println("That user already has access to that pen.");
						} else {
							Create.permit(userName, penName);
							return true;
						}
					}
				}
			}
			return true;
		case 4:
			while (loop == true) {// withdraw
				penName = ReadFromUser.readAccount(userID, 1);
				if (penName.contentEquals("")) {
					return true;
				} else if (penName.equals("error")) {
					System.out.println("You don't have a pen by that name. Please say a different name.");
				} else {
					loop = false;
				}
			}
			loop = true;
			while (loop == true) {
				System.out.println(
						"To withdraw more than one kind, enter the number of cows, then sheep, then goats to be withdrawn.");
				System.out.println("To withdraw only one kind, enter the name of that animal, then how many.");
				int[] result = ReadFromUser.readAmount();
				if (result.length == 2) {
					check = Amount.withdraw(penName, result[0], result[1]);
					if (check == 1) {
						loop = false;
					}
				} else if (result.length == 3) {
					check = Amount.withdraw(penName, result[0], result[1], result[2]);
					if (check == 1) {
						loop = false;
					}
				} else {
					return true;
				}
			}
			return true;
		case 5:
			while (loop == true) {// deposit
				penName = ReadFromUser.readAccount(userID, 1);
				if (penName.contentEquals("")) {
					return true;
				} else if (penName.equals("error")) {
					System.out.println("You don't have a pen by that name. Please say a different name.");
				} else {
					loop = false;
				}
			}
			loop = true;
			while (loop == true) {
				System.out.println(
						"To deposit more than one kind, enter the number of cows, then sheep, then goats to be deposited.");
				System.out.println("To deposit only one kind, enter the name of that animal, then how many.");
				int[] result = ReadFromUser.readAmount();
				if (result.length == 2) {
					check = Amount.deposit(penName, result[0], result[1]);
					if (check == 1) {
						loop = false;
					}
				} else if (result.length == 3) {
					check = Amount.deposit(penName, result[0], result[1], result[2]);
					if (check == 1) {
						loop = false;
					}
				} else {
					return true;
				}
			}
			return true;
		case 6:
			while (loop == true) {// transfer
				System.out.print("For the pen the animals will be taken from: ");
				sendname = ReadFromUser.readAccount(userID, 1);
				if (sendname.contentEquals("")) {
					return true;
				} else if (sendname.equals("error")) {
					System.out.println("You don't have a pen by that name. Please say a different name.");
				} else {
					loop = false;
				}
			}
			loop = true;
			while (loop == true) {// transfer
				System.out.print("For the pen the animals will be sent to: ");
				recievename = ReadFromUser.readAccount(userID, 2);
				if (recievename.contentEquals("")) {
					return true;
				} else if (recievename.equals("error")) {
					System.out.println("There isn't a pen by that name. Please say a different name.");
				} else {
					loop = false;
				}
			}
			loop = true;
			while (loop == true) {
				System.out.println(
						"To transfer more than one kind, enter the number of cows, then sheep, then goats to be transfered.");
				System.out.println("To transfer only one kind, enter the name of that animal, then how many.");
				int[] result = ReadFromUser.readAmount();
				if (result.length == 2) {
					check = Amount.transfer(sendname, result[0], result[1], recievename);
					if (check == 1) {
						loop = false;
					}
				} else if (result.length == 3) {
					check = Amount.transfer(sendname, result[0], result[1], result[2], recievename);
					if (check == 1) {
						loop = false;
					}
				} else {
					return true;
				}
			}
			return true;
		case 3:
			boolean close = confirm();
			if (close) {
				Close.user(userID);
				System.out.println("Closing account. Thank you for choosing Green Pasture Stables.");
				return false;
			} else {
				System.out.println("'Close Account' not entered. Your account is still active.");
				return true;
			}
		default:
			System.out.println("Logging out. Thank you for choosing Green Pasture Stables.");
			return false;
		}

	}

	static boolean confirm() {
		System.out.println("Are you sure you want to close your account? If so, say 'Close Account'.");
		int confirm = ReadFromUser.readList("close account", "");
		if (confirm == 0) {
			return true;
		} else {
			return false;
		}
	}
}