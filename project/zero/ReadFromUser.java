package project.zero;

import java.util.Scanner;

class ReadFromUser {

	static int readLogIn(int log) {
		int userID = 0;
		while (userID == 0) {
			String username = read.nextLine();
			if (username.contains("exit")) {
				break;
			}
			String passcode = read.nextLine();
			if (passcode.contains("exit")) {
				break;
			}
			if (log == 0) {
				LogIn login = new LogIn();
				userID = login.user(username, passcode);
			} else if (log == 1) {
				Create create = new Create();
				userID = create.user(username, passcode);
			} else {
				return 0;
			}
		}
		return userID;
	}

	static int readList(String... a) {
		boolean validString = false;
		int x = 1000;
		while (validString == false) {
			String input = read.nextLine();
			input = input.toLowerCase();
			for (int i = 0; i < a.length; i++) {
				if (input.contains(a[i]) || input.contentEquals(String.valueOf(i + 1))) {
					validString = true;
					x = i;
					i=a.length;
				}
			}
			if (x == 1000) {
				System.out.println("Your statement was not understood. Please check your pronunciation.");
			}
		}
		return x;
	}

	static String readUser() {
		System.out.println("Please enter the name of the other user");
		String userName = read.nextLine();
		if (userName.contains("exit")) {
			return "";
		} else if (Validate.isUserNamed(userName) == 0) {
			System.out.println("There is no user by that name.");
			return "error";
		} else {
			return userName;
		}
	}

	static String readAccount(int userID, int option) {
		System.out.println("Please enter the pen name.");
		String penName = read.nextLine();
		if (penName.contains("exit")) {
			return "";
		} else if (Validate.isAccountNamed(penName) == 0 && option == 0) {
			return penName;
		} else if (Validate.isUserAllowed(userID, penName) == 1 && option == 1) {
			return penName;
		} else if (option == 2) {
			return penName;
		} else {
			return "error";
		}
	}

	static int[] readAmount() {
		while (true) {
			String type = read.nextLine();
			type = type.toLowerCase();
			if (type.contains("exit")) {
				return null;
			} else if (type.contains("cow")) {
				int[] result = new int[2];
				result[0] = 0;
				result[1] = readInt();
				if (result[1]==-1) {
					return null;
				}
				return result;
			} else if (type.contains("sheep")) {
				int[] result = new int[2];
				result[0] = 1;
				result[1] = readInt();
				if (result[1]==-1) {
					return null;
				}
				return result;
			} else if (type.contains("goat")) {
				int[] result = new int[2];
				result[0] = 2;
				result[1] = readInt();
				if (result[1]==-1) {
					return null;
				}
				return result;
			} else {
				try {
					int i = Integer.parseInt(type);
					if (i >= 0) {
						int[] result = new int[3];
						result[0] = i;
						result[1] = readInt();
						if (result[1]==-1) {
							return null;
						}
						result[2] = readInt();
						if (result[2]==-1) {
							return null;
						}
						return result;
					} else {
						System.out.println("Please enter an animal type, or a positive whole number");
					}
				} catch (NumberFormatException e) {
					System.out.println("Please enter an animal type, or a positive whole number");
				}
			}

		}
	}

	static int readInt() {
		while (true) {
			if (read.hasNextInt()) {
				int i = read.nextInt();
				read.nextLine();
				if (i >= 0) {
					return i;
				} else {
					System.out.println("Please enter a positive whole number.");
				}
			} else {
				String exittest = read.nextLine();
				if (exittest.contains("exit")) {
					return -1;
				} else {
					System.out.println("Please enter a positive whole number.");
				}
			}
		}

	}

	static Scanner read = new Scanner(System.in);
}
