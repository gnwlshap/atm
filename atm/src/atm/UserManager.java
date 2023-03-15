package atm;

import java.util.ArrayList;

public class UserManager {
	private static ArrayList<User> list = new ArrayList<>();
	
	// Create
	public boolean addUser(User user) {
		User check = getUserById(user.getId());
		// 자체 확인
		if(check == null) {
			list.add(user);
			return true;
		}
		else
			return false;
	}
	
	public void addUserAcc(int index, Account acc) {
		list.get(index).addAcc(acc);
	}
	// Read
	public User getUser(int index) {
		User user = list.get(index);
		// 사본 제공
		User reqObj = new User(user.getId(), user.getPw(), user.getName(), user.getUserAccList());
		return reqObj;
	}
	
	public User getUserById(String id) {
		User user = null;
		
		int index = indexOfById(id);
		if(index != -1)
			user = getUser(index);
		
		return user;
	}
	
	public int indexOfById(String id) {
		int index = -1;
		for(User user : list) {
			if(user.getId().equals(id))
				index = list.indexOf(user);
		}
		return index;
	}
	
	public int getUserSize() {
		return list.size();
	}
	// Update
	public void setList(ArrayList<User> list) {
		UserManager.list = list;
	}
	
	public void setUser(int index, User user) {
		list.set(index, user);
	}
	
	public void setUserAcc(User user, Account acc) {
		int index = indexOfById(user.getId());
		
		list.get(index).addAcc(acc);
	}
	public void setUserAccMoney(String id, int index, int money) {
		int idx = indexOfById(id);
		
		list.get(idx).setAccMoney(index, money);
	}
	public void setUserAccMoney(String id, String accNum, int money) {
		int idx = indexOfById(id);
		int index = list.get(idx).indexOfByAccNum(accNum);
		
		list.get(idx).setAccMoney(index, money);
	}
	// Delete
	public void removeUser(int index) {
		list.remove(index);
	}
	
	public void removeUserAcc(String id, int index) {
		int idx = indexOfById(id);
		
		list.get(idx).removeAcc(index);
	}
	
	public void removeUserById(String id) {
		int index = -1;
		for(int i=0; i<list.size(); i++)
			if(list.get(i).getId().equals(id))
				index = i;
			
		list.remove(index);
	}
}
