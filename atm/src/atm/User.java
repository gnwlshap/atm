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
	
	public User(String id, String pw, String name, ArrayList<Account> accs) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.accs = accs;
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
	public void addUserAcc(Account acc) {
		this.accs.add(acc);
	}
	public ArrayList<Account> getUserAccList() {
		return (ArrayList<Account>) this.accs.clone();
	}
	public Account getUserAcc(int index) {
		return this.accs.get(index);
	}
	public void removeUserAcc(int index) {
		this.accs.remove(index);
	}
	public int getUserAccSize() {
		int size = this.accs.size();
		
		return size;
	}
}
