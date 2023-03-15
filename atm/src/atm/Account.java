package atm;

public class Account {
	public static int LIMIT = 3;
	
	private String id;
	private String number;
	private int money;
	
	public Account(String id, String number) {
		this.id = id;
		this.number = number;
		this.money = 0;
	}
	
	public Account (String id, String number, int money) {
		this.id = id;
		this.number = number;
		this.money = money;
	}
	
	public String getAccNum() {
		return number;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getId() {
		return id;
	}
}
