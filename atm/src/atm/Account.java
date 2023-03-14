package atm;

public class Account {
	public static int LIMIT = 3;
	
	private String id;
	private String number;
	private int money;
	
	public Account(String id,String number) {
		this.number = number;
		this.money = 0;
	}
	public String getNumber() {
		return number;
	}
	public int getMoney() {
		return money;
	}
	public String getId() {
		return id;
	}
}
