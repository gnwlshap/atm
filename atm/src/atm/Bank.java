package atm;

import java.util.Random;
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
		System.out.println("2. ȸ��Ż��");
		System.out.println("3. �α���");
		System.out.println("4. �α׾ƿ�");
		System.out.println("5. ���°���");
		System.out.println("6. ����öȸ");
		System.out.println("");
	}
	
	private boolean isLoggedIn() {
		return this.log != -1; 
	}
	
	private void join() {
		if(!isLoggedIn()) {
			System.out.println("===== ȸ�� ���� =====");
			System.out.print("ID : ");
			String id = this.scan.next();
			if(!dupl(id)) {
				System.out.print("PW : ");
				String pw = this.scan.next();
				System.out.print("name : ");
				String name = this.scan.next();
				User user = new User(id,pw,name);
				
				this.um.addList(user);
				System.out.println("\n...ȸ������ �Ϸ�\n");
			}
			else
				System.out.println("�ߺ��� ID�Դϴ�.\n");
		}
		else
			System.out.println("\n�α׾ƿ� ���¿��� ������ �޴��Դϴ�.\n");
	}
	
	private boolean dupl(String id) {
		boolean dupl = false;
		for(int i=0; i<this.um.getList().size(); i++)
			if(id.equals(this.um.getUser(i).getId()))
				dupl = true;
		
		return dupl;
	}
	
	private void leave() {
		if(isLoggedIn()) {
			System.out.println("===== ȸ�� Ż�� =====");
			System.out.print("PW : ");
			String pw = this.scan.next();
			if(this.um.getUser(this.log).getPw().equals(pw)) {
				um.removeUser(this.log);
				this.log = -1;
				System.out.println("\n...ȸ��Ż�� �Ϸ�\n");
			}
			else
				System.out.println("\n��й�ȣ�� Ȯ���ϼ���.\n");
		}
		else
			System.out.println("\n�α��� ���¿��� ������ �޴��Դϴ�.\n");
	}
	
	private void login() {
		if(!isLoggedIn()) {
			System.out.print("ID : ");
			String id = this.scan.next();
			System.out.print("PW : ");
			String pw = this.scan.next();
			for(int i=0; i<this.um.getList().size(); i++) {
				if(this.um.getUser(i).getId().equals(id) && this.um.getUser(i).getPw().equals(pw))
					this.log = i;
			}
			if(this.log != -1)
				System.out.println("\n...�α��� �Ϸ�\n");
			else
				System.out.println("\nȸ�������� Ȯ���ϼ���.\n");
		}
		else
			System.out.println("\n�α׾ƿ� ���¿��� ������ �޴��Դϴ�.\n");
	}
	
	private void logout() {
		if(isLoggedIn()) {
			this.log = -1;
			System.out.println("\n...�α׾ƿ� �Ϸ�\n");
		}
		else
			System.out.println("\n�α��� ���¿��� ������ �޴��Դϴ�.\n");
	}
	
	private void createAcc() {
		if(isLoggedIn()) {
			Random ran = new Random();
			
			User user = this.um.getList().get(this.log);
			
			String accNum = "";
			for(int i=0; i<3; i++) {
				accNum += ran.nextInt(8999)+1001;
				if(i<2)
					accNum += "-";
			}
			Account acc = new Account(user.getId(), accNum);
			
			if(user.getAccountSize() < Account.LIMIT) {
				this.am.addList(acc);
				user.addUserAcc(acc);
				System.out.println();
				System.out.println("\n...���°��� �Ϸ�\n");
				System.out.println("�߱޵� ���¹�ȣ : "+accNum);
			}
			else
				System.out.println("�� �̻� ������ �� �����ϴ�.");
			

		}
		else
			System.out.println("\n�α��� ���¿��� ������ �޴��Դϴ�.\n");
	}
	
	private void deleteAcc() {
		if(isLoggedIn()) {
			
			User user = this.um.getList().get(this.log);
			
			for(int i=0; i<user.getAccountSize(); i++) {
				System.out.println(i+1+". "+user.getUserAcc(i).getNumber());
			}
			
			System.out.print("������ ���¹�ȣ : ");
			int sel = this.scan.nextInt()-1;
			
			int idx = -1;
			for(int i=0; i<this.am.getList().size(); i++)
				if(user.getUserAcc(sel).getNumber().equals(this.am.getAcc(i).getNumber()))
					idx = i;
			
			if(sel < user.getAccountSize()) {
				user.removeUserAcc(sel);
				this.am.removeAcc(idx);
				System.out.println("\n...���� öȸ �Ϸ�\n");
			}
		}
		else
			System.out.println("\n�α��� ���¿��� ������ �޴��Դϴ�.\n");
	}
	
	private void checkAllAcc() {
		for(int i=0; i<this.am.getList().size(); i++) {
			System.out.println(this.am.getList().get(i).getNumber());
		}
		
	}
	
	public void run() {
		while(true) {
			printMenu();
			System.out.print("�޴���ȣ �Է� : ");
			int sel = this.scan.nextInt();
			if(sel == 1) join();
			else if(sel == 2) leave();
			else if(sel == 3) login();
			else if(sel == 4) logout();
			else if(sel == 5) createAcc();
			else if(sel == 6) deleteAcc();
			else if(sel == 7) checkAllAcc();
		}
	}
}
