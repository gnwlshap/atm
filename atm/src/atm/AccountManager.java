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
	
	public Account getUserById(String id) {
		int index = -1;
		return getAcc(index);
	}
	
	public void setAcc(int index, Account acc) {
		this.list.set(index, acc);
	}
	
	public void removeAcc(int index) {
		this.list.remove(index);
	}
	
	public void removeAccById(String id) {
		int index = -1;
		for(int i=0; i<this.list.size(); i++)
			if(this.list.get(i).getId().equals(id))
				index = i;
			
		this.list.remove(index);
	}
	
}
