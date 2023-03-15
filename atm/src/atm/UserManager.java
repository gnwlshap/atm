package atm;

import java.util.ArrayList;

public class UserManager {
	private static ArrayList<User> list = new ArrayList<>();
	
	// Create
	public boolean addUser(User user) {
		User check = getUserById(user.getId());
		// 자체 확인
		if(check == null) {
			this.list.add(user);
			return true;
		}
		else
			return false;
	}
	// Read
	public User getUser(int index) {
		User user = this.list.get(index);
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
				index = this.list.indexOf(user);
		}
		return index;
	}
	
	public int getUserSize() {
		return this.list.size();
	}
	// Update
	public void setUser(int index, User user) {
		this.list.set(index, user);
	}
	// Delete
	public void removeUser(int index) {
		this.list.remove(index);
	}
	
	public void removeUserById(String id) {
		int index = -1;
		for(int i=0; i<this.list.size(); i++)
			if(this.list.get(i).getId().equals(id))
				index = i;
			
		this.list.remove(index);
	}
}
