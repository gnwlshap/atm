package atm;

import java.util.ArrayList;

public class UserManager {
	private static ArrayList<User> list = new ArrayList<>();
	 
	public static ArrayList<User> getList() {
		return list;
	}
	
	public void addList(User user) {
		this.list.add(user);
	}
	public User getUser(int index) {
		User user = this.list.get(index);
		// 사본 제공
		User reqObj = new User(user.getId(), user.getPw(), user.getName());
		return reqObj;
	}
	
	public User getUserById(String id) {
		int index = -1;
		return getUser(index);
	}
	
	public void setUser(int index, User user) {
		this.list.set(index, user);
	}
	
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
