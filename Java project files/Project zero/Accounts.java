package project.zero;

class Accounts {
	private int account_id;
	private String account_name;
	private String account_type;
	private int amount_Cows;
	private int amount_Sheep;
	private int amount_Goats;
	private String users;
	
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public int getAccount_id() {
		return account_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public int getAmount_Cows() {
		return amount_Cows;
	}
	public void setAmount_Cows(int amount_Cows) {
		this.amount_Cows = amount_Cows;
	}
	public int getAmount_Sheep() {
		return amount_Sheep;
	}
	public void setAmount_Sheep(int amount_Sheep) {
		this.amount_Sheep = amount_Sheep;
	}
	public int getAmount_Goats() {
		return amount_Goats;
	}
	public void setAmount_Goats(int amount_Goats) {
		this.amount_Goats = amount_Goats;
	}
	Accounts(int accountID, String accountName, String accountType, int cows, int sheep, int goats) {
		super();
		this.account_id = accountID;
		this.account_name = accountName;
		this.account_type = accountType;
		this.amount_Cows = cows;
		this.amount_Sheep = sheep;
		this.amount_Goats = goats;
	}
}
