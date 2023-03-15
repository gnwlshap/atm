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
		System.out.println("1. 회원가입");
		System.out.println("2. 로그인");
		System.out.println("0. 종료");
	}
	
	private void printLoginMenu() {
		System.out.printf("===== %s =====\n",this.name);
		System.out.printf("=== %s님 하이 ===\n",this.um.getUser(this.log).getName());
		System.out.println("1. 입급");
		System.out.println("2. 출금");
		System.out.println("3. 조회");
		System.out.println("4. 이체");
		System.out.println("5. 계좌개설");
		System.out.println("6. 계좌철회");
		System.out.println("7. 회원탈퇴");
		System.out.println("8. 로그아웃");
	}
	
	private boolean isLoggedIn() {
		return this.log != -1; 
	}
	
	private void join() {
		System.out.println("===== 회원 가입 =====");
		System.out.print("ID : ");
		String id = this.scan.next();
		System.out.print("PW : ");
		String pw = this.scan.next();
		System.out.print("name : ");
		String name = this.scan.next();
		User user = new User(id,pw,name);
		if(this.um.addUser(user)) {
			System.out.println("\n...회원가입 완료\n");
		}
		else
			System.out.println("중복된 ID입니다.\n");
	}
	
	private void leave() {
		User user = this.um.getUser(this.log);
		
		System.out.println("===== 회원 탈퇴 =====");
		System.out.print("PW : ");
		String pw = this.scan.next();
		if(user.getPw().equals(pw)) {
			this.um.removeUser(this.log);
			this.am.removeAccountById(user.getId(), user.getUserAccSize());
			this.log = -1;
			System.out.println("\n...회원탈퇴 완료\n");
		}
		else
			System.out.println("\n비밀번호를 확인하세요.\n");
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
			System.out.println("\n...로그인 완료\n");
		else
			System.out.println("\n회원정보를 확인하세요.\n");
	}
	
	private void logout() {
		this.log = -1;
		System.out.println("\n...로그아웃 완료\n");
	}
	
	private void createAcc() {
		User user = this.um.getUser(this.log);
		
		String accNum = this.am.accNumGenerator();
		
		Account acc = new Account(user.getId(), accNum);
		
		if(user.getUserAccSize() < Account.LIMIT) {
			this.am.addAcc(acc);
			user.addUserAcc(acc);
			System.out.println();
			System.out.println("\n...계좌개설 완료\n");
			System.out.println("발급된 계좌번호 : "+accNum);
		}
		else
			System.out.println("\n더 이상 개설할 수 없습니다.\n");
	}
	
		
	private void deleteAcc() {
		User user = this.um.getUser(this.log);
		
		if(printMyAcc()) {
			System.out.print("계좌 선택 : ");
			int sel = this.scan.nextInt()-1;
			
			int idx = -1;
			
			if(sel < user.getUserAccSize()) {
				for(int i=0; i<this.am.getAllAccSize(); i++)
					if(user.getUserAcc(sel).getAccNum().equals(this.am.getAcc(i).getAccNum()))
						idx = i;
				
				this.am.removeAcc(idx);
				user.removeUserAcc(sel);
				System.out.println("\n...계좌 철회 완료\n");
			}
			else
				System.out.println("\n번호를 확인하세요.\n");
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
			System.out.println("\n보유중인 계좌가 없습니다.\n");
			return false;
		}
	}
	
	private void deposit() {
		if(printMyAcc()) {
			
			System.out.print("계좌 선택 : ");
			int sel = this.scan.nextInt()-1;
			
			System.out.print("입금 금액 : ");
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
			System.out.print("메뉴번호 입력 : ");
			int sel = this.scan.nextInt();
			if(sel == 1) join();
			else if(sel == 2) login();
			else if(sel == 0) {
				System.out.println("...종료");
				break;
			}
			while(isLoggedIn()) {
				printLoginMenu();
				System.out.print("메뉴번호 입력 : ");
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
