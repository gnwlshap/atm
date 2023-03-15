package atm;

import java.util.Scanner;

public class Bank {
	private Scanner scan;
	private String name;
	private int log;
	
	private UserManager um;
	private AccountManager am;
	
	public Bank(String name) {
		this.scan = new Scanner(System.in);
		this.name = name;
		this.log = -1;
		um = new UserManager();
		am = new AccountManager();
	}
	
	private void printMenu() {
		System.out.printf("===== %s =====\n",this.name);
		System.out.println("1. ȸ������");
		System.out.println("2. �α���");
		System.out.println("0. ����");
	}
	
	private void printLoginMenu() {
		System.out.printf("===== %s =====\n",this.name);
		System.out.printf("=== %s�� ���� ===\n",this.um.getUser(this.log).getName());
		System.out.println("1. �Ա�");
		System.out.println("2. ���");
		System.out.println("3. ��ȸ");
		System.out.println("4. ��ü");
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
			this.am.removeAccountById(user.getId(), user.getUserAccSize());
			this.log = -1;
			System.out.println("\n...ȸ��Ż�� �Ϸ�\n");
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
		
		if(user.getUserAccSize() < Account.LIMIT) {
			this.am.addAcc(acc);
			user.addUserAcc(acc);
			System.out.println();
			System.out.println("\n...���°��� �Ϸ�\n");
			System.out.println("�߱޵� ���¹�ȣ : "+accNum);
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
			
			if(sel < user.getUserAccSize()) {
				for(int i=0; i<this.am.getAllAccSize(); i++)
					if(user.getUserAcc(sel).getAccNum().equals(this.am.getAcc(i).getAccNum()))
						idx = i;
				
				this.am.removeAcc(idx);
				user.removeUserAcc(sel);
				System.out.println("\n...���� öȸ �Ϸ�\n");
			}
			else
				System.out.println("\n��ȣ�� Ȯ���ϼ���.\n");
		}
	}
	
	private boolean printMyAcc() {
		User user = this.um.getUser(this.log);
		
		if(user.getUserAccSize() > 0) {
			for(int i=0; i<user.getUserAccSize(); i++)
				System.out.println(i+1+". "+user.getUserAcc(i).getAccNum());
			return true;
		}
		else {
			System.out.println("\n�������� ���°� �����ϴ�.\n");
			return false;
		}
	}
	
	private void deposit() {
		if(printMyAcc()) {
			
			System.out.print("���� ���� : ");
			int sel = this.scan.nextInt()-1;
			
			System.out.print("�Ա� �ݾ� : ");
			int money = this.scan.nextInt();
		}
		
		
		
	}
	private void withdraw() {
		
	}
	private void checkMyInfo() {
		
	}
	private void transfer() {
		
	}
	
	private void checkAllAcc() {
		for(int i=0; i<this.am.getAllAccSize(); i++) {
			System.out.println(this.am.getAcc(i).getAccNum());
		}
	}
	
	public void run() {
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
				else if(sel == 3) checkMyInfo();
				else if(sel == 4) transfer();
				else if(sel == 5) createAcc();
				else if(sel == 6) deleteAcc();
				else if(sel == 7) leave();
				else if(sel == 8) logout();
			}
		}
	}
}
