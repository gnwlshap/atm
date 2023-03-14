package atm;

import java.util.ArrayList;

public class User {
	private String id;
	private String pw;
	private String name;
	public ArrayList<Account> accs;
	
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
	public Account getUserAcc(int index) {
		return this.accs.get(index);
	}
	public void addUserAcc(Account acc) {
		this.accs.add(acc);
	}
	public void removeUserAcc(int index) {
		this.accs.remove(index);
	}
	public int getAccountSize() {
		int size = this.accs.size();
		
		return size;
	}
}
