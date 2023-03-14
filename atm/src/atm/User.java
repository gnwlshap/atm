package atm;

import java.util.ArrayList;

public class User {
	private String id;
	private String pw;
	private String name;
	private ArrayList<Account> accs;
	
	public User(String id, String pw, String name) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.accs = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Account> getUserAccList() {
		return accs;
	}
}
