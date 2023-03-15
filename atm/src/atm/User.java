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
	public void addAcc(Account acc) {
		this.accs.add(acc);
	}
	public ArrayList<Account> getUserAccList() {
		return (ArrayList<Account>) this.accs.clone();
	}
	public Account getAcc(int index) {
		Account acc = this.accs.get(index);
		
		Account reqObj = new Account(acc.getId(), acc.getAccNum(), acc.getMoney());
		return reqObj;
	}
	
	public int indexOfByAccNum(String accNum) {
		int index = -1;
		for(int i=0; i<this.accs.size(); i++) {
			if(getAcc(i).getAccNum().equals(accNum))
				index = i;
		}
		return index;
	}
	
	public int getAccSize() {
		int size = this.accs.size();
		
		return size;
	}
	public void setAccMoney(int index, int money) {
		this.accs.get(index).setMoney(money);
	}
	public void removeAcc(int index) {
		this.accs.remove(index);
	}
}
