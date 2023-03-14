package atm;

import java.util.ArrayList;

public class AccountManager {
	private static ArrayList<Account> list = new ArrayList<>();
	
	public static ArrayList<Account> getList() {
		return list;
	}
	
	public void addList(Account acc) {
		this.list.add(acc);
	}
	public Account getAcc(int index) {
		Account acc = list.get(index);
		// 사본 제공
		Account reqObj = new Account(acc.getId(),acc.getNumber());
		return reqObj;
	}
	
	public void setAcc(int index, Account acc) {
		this.list.set(index, acc);
	}
	
	public void removeAcc(int index) {
		this.list.remove(index);
	}
	
}
