package atm;

import java.util.Scanner;

public class Bank {
	private Scanner scan;
	private String name;
	private int log;
	
	private UserManager um;
	private AccountManager am;
	private FileManager fm;
	
	public Bank(String name) {
		this.scan = new Scanner(System.in);
		this.name = name;
		this.log = -1;
		um = new UserManager();
		am = new AccountManager();
		fm = new FileManager();
	}
	
	private void printMenu() {
		System.out.printf("====== %s ======\n",this.name);
		System.out.println("1. ȸ������");
		System.out.println("2. �α���");
		System.out.println("0. ����");
	}
	
	private void printLoginMenu() {
		System.out.printf("====== %s ======\n",this.name);
		System.out.printf("  %s�� ȯ���մϴ�!!!\n",this.um.getUser(this.log).getName());
		System.out.println("1. �Ա�");
		System.out.println("2. ���");
		System.out.println("3. ��ü");
		System.out.println("4. ��ȸ");
		System.out.println("5. ���°���");
		System.out.println("6. ����öȸ");
		System.out.println("7. ȸ��Ż��");
		System.out.println("8. �α׾ƿ�");
	}
	
	private boolean isLoggedIn() {
		return this.log != -1; 
	}
	
	private void join() {
		System.out.println("===== ȸ�� ���� =====");
		System.out.print("ID : ");
		String id = this.scan.next();
		System.out.print("PW : ");
		String pw = this.scan.next();
		System.out.print("name : ");
		String name = this.scan.next();
		User user = new User(id,pw,name);
		if(this.um.addUser(user)) {
			System.out.println("\n...ȸ������ �Ϸ�\n");
			saveUserFile();
		}
		else
			System.out.println("�ߺ��� ID�Դϴ�.\n");
	}
	
	private void leave() {
		User user = this.um.getUser(this.log);
		
		System.out.println("===== ȸ�� Ż�� =====");
		System.out.print("PW : ");
		String pw = this.scan.next();
		if(user.getPw().equals(pw)) {
			this.um.removeUser(this.log);
			this.am.removeAccountById(user.getId(), user.getAccSize());
			this.log = -1;
			System.out.println("\n...ȸ��Ż�� �Ϸ�\n");
			saveUserFile();
		}
		else
			System.out.println("\n��й�ȣ�� Ȯ���ϼ���.\n");
	}
	
	private void login() {
		System.out.print("ID : ");
		String id = this.scan.next();
		System.out.print("PW : ");
		String pw = this.scan.next();
		for(int i=0; i<this.um.getUserSize(); i++) {
			if(this.um.getUser(i).getId().equals(id) && this.um.getUser(i).getPw().equals(pw))
				this.log = i;
		}
		if(this.log != -1)
			System.out.println("\n...�α��� �Ϸ�\n");
		else
			System.out.println("\nȸ�������� Ȯ���ϼ���.\n");
	}
	
	private void logout() {
		this.log = -1;
		System.out.println("\n...�α׾ƿ� �Ϸ�\n");
	}
	
	private void createAcc() {
		User user = this.um.getUser(this.log);
		
		String accNum = this.am.accNumGenerator();
		
		Account acc = new Account(user.getId(), accNum);
		
		if(user.getAccSize() < Account.LIMIT) {
			this.am.addAcc(acc);
			this.um.addUserAcc(this.log, acc);
			System.out.println();
			System.out.println("\n...���°��� �Ϸ�\n");
			System.out.println("�߱޵� ���¹�ȣ : "+accNum);
			saveAccFile();
		}
		else
			System.out.println("\n�� �̻� ������ �� �����ϴ�.\n");
	}
	
		
	private void deleteAcc() {
		User user = this.um.getUser(this.log);
		
		if(printMyAcc()) {
			System.out.print("���� ���� : ");
			int sel = this.scan.nextInt()-1;
			
			int idx = -1;
			
			if(sel < user.getAccSize()) {
				for(int i=0; i<this.am.getAllAccSize(); i++)
					if(user.getAcc(sel).getAccNum().equals(this.am.getAcc(i).getAccNum()))
						idx = i;
				
				this.am.removeAcc(idx);
				this.um.removeUserAcc(user.getId(), sel);
				System.out.println("\n...���� öȸ �Ϸ�\n");
				saveAccFile();
			}
			else
				System.out.println("\n��ȣ�� Ȯ���ϼ���.\n");
		}
	}
	
	private boolean printMyAcc() {
		User user = this.um.getUser(this.log);
		
		if(user.getAccSize() > 0) {
			for(int i=0; i<user.getAccSize(); i++)
				System.out.println(i+1+". "+user.getAcc(i).getAccNum());
			System.out.println();
			return true;
		}
		else {
			System.out.println("\n�������� ���°� �����ϴ�.\n");
			return false;
		}
	}
	
	private void deposit() {
		User user = this.um.getUser(this.log);
		if(printMyAcc()) {
			System.out.print("���� ���� : ");
			int sel = this.scan.nextInt()-1;
			
			if(sel < user.getAccSize()) {
				System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
				System.out.print("�Ա� �ݾ� : ");
				int money = this.scan.nextInt();
				
				if(money > 0) {
					this.um.setUserAccMoney(user.getId(), sel, money);
					this.am.setAccMoney(sel, money);
					System.out.println("\n�Ա� �Ϸ�.");
					System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
				}
			}
		}
	}
	
	private void withdraw() {
		User user = this.um.getUser(this.log);
		if(printMyAcc()) {
			System.out.print("���� ���� : ");
			int sel = this.scan.nextInt()-1;
			
			if(sel < user.getAccSize()) {
				System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
				System.out.print("��� �ݾ� : ");
				int money = this.scan.nextInt();
				
				int curMoney = user.getAcc(sel).getMoney();
				
				if(money <= curMoney) {
					this.um.setUserAccMoney(user.getId(), sel, curMoney - money);
					this.am.setAccMoney(sel, curMoney - money);
					System.out.println("\n��� �Ϸ�.");
					System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
				}
				else
					System.out.println("\n�ܾ��� �����մϴ�.\n");
			}
		}
	}
	
	private void transfer() {
		User user = this.um.getUser(this.log);
		if(printMyAcc()) {
			System.out.print("���� ���� : ");
			int sel = this.scan.nextInt()-1;
			
			if(sel < user.getAccSize()) {
				System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
				System.out.print("��ü�� ���¹�ȣ : ");
				String transferedAccNum = this.scan.next();
				
				boolean checkTransfer = false;
				for(int i=0; i<this.am.getAllAccSize(); i++) {
					if(this.am.getAcc(i).getAccNum().equals(transferedAccNum)) {
						checkTransfer = true;
						
						User transferedUser = this.um.getUserById(this.am.getAcc(i).getId());
						
						System.out.printf("��ü ���¹�ȣ �����ָ� : %s\n",transferedUser.getName());
						
						System.out.print("��ü �ݾ� : ");
						int money = this.scan.nextInt();
						
						int curMoney = user.getAcc(sel).getMoney();
						int transferedCurMoney = this.am.getAcc(i).getMoney();
						
						
						if(money <= curMoney) {
							this.um.setUserAccMoney(user.getId(), sel, curMoney - money);
							this.um.setUserAccMoney(transferedUser.getId(), transferedAccNum, transferedCurMoney + money);
							this.am.setAccMoney(sel, curMoney - money);
							this.am.setAccMoney(this.am.indexOfByAccNum(transferedAccNum), transferedCurMoney + money);
							System.out.println("\n��ü �Ϸ�.");
							System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
						}
						else
							System.out.println("\n�ܾ��� �����մϴ�.\n");
					}
				}
				if(!checkTransfer)
					System.out.println("\n���� ���¹�ȣ�Դϴ�.\n");
			}
		}
	}
	
	private void checkMyInfo() {
		
		System.out.printf("\n�̸� : %s\nID : %s\n���� ���� : \n",this.um.getUser(this.log).getName(), this.um.getUser(this.log).getId());
		if(printMyAcc()) {
			checkMyAccMoney();
		}
	}
	
	private void checkMyAccMoney() {
		User user = this.um.getUser(this.log);
		System.out.print("�ܾ� ��ȸ(��ȣ) : ");
		int sel = this.scan.nextInt()-1;
		
		if(sel < user.getAccSize()) {
			System.out.printf("\n���� �ܾ� : %d��\n\n",user.getAcc(sel).getMoney());
		}
	}
	
	private void saveUserFile() {
		String userFile = "";
		for(int i=0; i<this.um.getUserSize(); i++) {
			userFile += this.um.getUser(i).getId()+"/";
			userFile += this.um.getUser(i).getPw()+"/";
			userFile += this.um.getUser(i).getName()+"\n";
		}
		this.fm.saveFile("user.txt", userFile);
	}
	
	private void saveAccFile() {
		String accFile = "";
		for(int i=0; i<this.am.getAllAccSize(); i++) {
			accFile += this.am.getAcc(i).getId()+"/";
			accFile += this.am.getAcc(i).getAccNum()+"/";
			accFile += this.am.getAcc(i).getMoney()+"\n";
		}
		this.fm.saveFile("account.txt", accFile);
	}
	
	private void loadFile() {
		String accFile = this.fm.loadFile("account.txt"); 
		if(accFile != "") {
			String[] accFileLines = accFile.split("\n");
			for(int i=0; i<accFileLines.length; i++) {
				String id = accFileLines[i].split("/")[0];
				String accNum = accFileLines[i].split("/")[1];
				int money = Integer.parseInt(accFileLines[i].split("/")[2]);
				
				Account acc = new Account(id, accNum, money);
				
				this.am.addAcc(acc);
			}
		}
		String userFile = this.fm.loadFile("user.txt");
		if(userFile != "") {
			String[] userFileLines = userFile.split("\n");
			for(int i=0; i<userFileLines.length; i++) {
				String id = userFileLines[i].split("/")[0];
				String pw = userFileLines[i].split("/")[1];
				String name = userFileLines[i].split("/")[2];
				
				User user = new User(id, pw, name);
				
				this.um.addUser(user);
			}
		}
		
		for(int i=0; i<this.am.getAllAccSize(); i++) {
			for(int j=0; j<this.um.getUserSize(); j++) {
				if(this.am.getAcc(i).getId().equals(this.um.getUser(j).getId())) {
					this.um.setUserAcc(this.um.getUser(j), this.am.getAcc(i));
				}
			}
		}
	}
	
	
	public void run() {
		loadFile();
		while(true) {
			printMenu();
			System.out.print("�޴���ȣ �Է� : ");
			int sel = this.scan.nextInt();
			if(sel == 1) join();
			else if(sel == 2) login();
			else if(sel == 0) {
				System.out.println("...����");
				break;
			}
			while(isLoggedIn()) {
				printLoginMenu();
				System.out.print("�޴���ȣ �Է� : ");
				sel = this.scan.nextInt();
				if(sel == 1) deposit();
				else if(sel == 2) withdraw();
				else if(sel == 3) transfer();
				else if(sel == 4) checkMyInfo();
				else if(sel == 5) createAcc();
				else if(sel == 6) deleteAcc();
				else if(sel == 7) leave();
				else if(sel == 8) logout();
			}
		}
	}
}
