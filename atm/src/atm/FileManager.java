package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileManager {
	private File file;
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	public void saveUserFile(User user) {
		this.file = new File("user.txt");
		
		try {
			this.fw = new FileWriter(this.file, true);
			
			this.fw.write(user.getId()+"/"+user.getPw()+"/"+user.getName()+"\n");
			
			this.fw.close();
			System.out.println("파일저장 완료");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("파일저장 실패");
		}
	}
	public void saveAccFile(Account acc) {
		this.file = new File("account.txt");
		
		try {
			this.fw = new FileWriter(this.file, true);
			
			this.fw.write(acc.getId()+"/"+acc.getAccNum()+"/"+acc.getMoney()+"\n");
			
			this.fw.close();
			System.out.println("파일저장 완료");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("파일저장 실패");
		}
	}
	public ArrayList<User> loadUserFile() {
		this.file = new File("user.txt");
		ArrayList<User> userList = null;
		if(this.file.exists()) {
			try {
				this.fr = new FileReader(this.file);
				this.br = new BufferedReader(this.fr);
				String[] dataLine = null;
				
				while(this.br.ready())
					dataLine = this.br.readLine().split("\n");
				
				for(int i=0; i<dataLine.length; i++) {
					String id = dataLine[i].split("/")[0];
					String pw = dataLine[i].split("/")[1];
					String name = dataLine[i].split("/")[2];
					
					User user = new User(id, pw, name);
					
					userList.add(user);
				}
				
				this.fr.close();
				this.br.close();
				
				return userList;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userList;
	}
	
	public ArrayList<Account> loadAccFile() {
		this.file = new File("account.txt");
		ArrayList<Account> accList = null;
		if(this.file.exists()) {
			try {
				this.fr = new FileReader(this.file);
				this.br = new BufferedReader(this.fr);
				String[] dataLine = null;

				
				while(this.br.ready())
					dataLine = this.br.readLine().split("\n");
				
				for(int i=0; i<dataLine.length; i++) {
					String id = dataLine[i].split("/")[0];
					String number = dataLine[i].split("/")[1];
					int money = Integer.parseInt(dataLine[i].split("/")[2]);
					
					Account acc = new Account(id, number, money);
					
					accList.add(acc);
				}
				
				this.fr.close();
				this.br.close();
				
				return accList;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return accList;
	}
}
