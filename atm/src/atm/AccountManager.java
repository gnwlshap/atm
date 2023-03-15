package atm;

import java.util.ArrayList;
import java.util.Random;

public class AccountManager {
	private static ArrayList<Account> list = new ArrayList<>();
	// Create
	public void addAcc(Account acc) {
		list.add(acc);
	}
	// Read
	public Account getAcc(int index) {
		Account acc = list.get(index);
		// 사본 제공
		Account reqObj = new Account(acc.getId(),acc.getAccNum(),acc.getMoney());
		return reqObj;
	}
	
	public Account getAccByAccNum(String accNum) {
		Account reqObj = null;
		for(Account acc : list) {
			if(acc.getAccNum().equals(accNum))
				reqObj = acc;
		}
		
		return reqObj;
	}
	
	public int indexOfByAccNum(String accNum) {
		int index = -1;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getAccNum().equals(accNum))
				index = i;
		}
		return index;
	}
	
	public int getAllAccSize() {
		return list.size();
	}

	// Update
	public void setAccMoney(int index, int money) {
		list.get(index).setMoney(money);
	}
	public void setAccMoney(String accNum, int money) {
		int index = indexOfByAccNum(accNum);
		
		list.get(index).setMoney(money);
	}
	// Delete
	public void removeAcc(int index) {
		list.remove(index);
	}
	
	public void removeAccountById(String id, int count) {
		for(int i=0; i<count; i++) {			
			int index = -1;
			for(Account acc : list) {			
				if(acc.getId().equals(id))
					index = list.indexOf(acc);
			}
			if(index != -1)
				removeAcc(index);
		}
	}
	
	public String accNumGenerator() {
		Random ran = new Random();
		String accNum = ""; 
		while(true) {
			int num1 = ran.nextInt(8999)+1001;
			int num2 = ran.nextInt(8999)+1001;
			accNum += num1 + "-" + num2;
			
			if(getAccByAccNum(accNum) == null)
				break;
		}
		return accNum;
	}
}

