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
		System.out.println("1. 회원가입");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 로그인");
		System.out.println("4. 로그아웃");
		System.out.println("5. 계좌개설");
		System.out.println("6. 계좌철회");
		System.out.println("");
	}
	
	private boolean isLoggedIn() {
		return this.log != -1; 
	}
	
	private void join() {
		if(!isLoggedIn()) {
			System.out.println("===== 회원 가입 =====");
			System.out.print("ID : ");
			String id = this.scan.next();
			if(!dupl(id)) {
				System.out.print("PW : ");
				String pw = this.scan.next();
				System.out.print("name : ");
				String name = this.scan.next();
				User user = new User(id,pw,name);
				
				this.um.addList(user);
				System.out.println("\n...회원가입 완료\n");
			}
			else
				System.out.println("중복된 ID입니다.\n");
		}
		else
			System.out.println("\n로그아웃 상태에서 가능한 메뉴입니다.\n");
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
			System.out.println("===== 회원 탈퇴 =====");
			System.out.print("PW : ");
			String pw = this.scan.next();
			if(this.um.getUser(this.log).getPw().equals(pw)) {
				um.removeUser(this.log);
				this.log = -1;
				System.out.println("\n...회원탈퇴 완료\n");
			}
			else
				System.out.println("\n비밀번호를 확인하세요.\n");
		}
		else
			System.out.println("\n로그인 상태에서 가능한 메뉴입니다.\n");
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
				System.out.println("\n...로그인 완료\n");
			else
				System.out.println("\n회원정보를 확인하세요.\n");
		}
		else
			System.out.println("\n로그아웃 상태에서 가능한 메뉴입니다.\n");
	}
	
	private void logout() {
		if(isLoggedIn()) {
			this.log = -1;
			System.out.println("\n...로그아웃 완료\n");
		}
		else
			System.out.println("\n로그인 상태에서 가능한 메뉴입니다.\n");
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
				System.out.println("\n...계좌개설 완료\n");
				System.out.println("발급된 계좌번호 : "+accNum);
			}
			else
				System.out.println("더 이상 개설할 수 없습니다.");
			

		}
		else
			System.out.println("\n로그인 상태에서 가능한 메뉴입니다.\n");
	}
	
	private void deleteAcc() {
		if(isLoggedIn()) {
			
			User user = this.um.getList().get(this.log);
			
			for(int i=0; i<user.getAccountSize(); i++) {
				System.out.println(i+1+". "+user.getUserAcc(i).getNumber());
			}
			
			System.out.print("삭제할 계좌번호 : ");
			int sel = this.scan.nextInt()-1;
			
			int idx = -1;
			for(int i=0; i<this.am.getList().size(); i++)
				if(user.getUserAcc(sel).getNumber().equals(this.am.getAcc(i).getNumber()))
					idx = i;
			
			if(sel < user.getAccountSize()) {
				user.removeUserAcc(sel);
				this.am.removeAcc(idx);
				System.out.println("\n...계좌 철회 완료\n");
			}
		}
		else
			System.out.println("\n로그인 상태에서 가능한 메뉴입니다.\n");
	}
	
	private void checkAllAcc() {
		for(int i=0; i<this.am.getList().size(); i++) {
			System.out.println(this.am.getList().get(i).getNumber());
		}
		
	}
	
	public void run() {
		while(true) {
			printMenu();
			System.out.print("메뉴번호 입력 : ");
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
