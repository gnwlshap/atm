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
		System.out.println("1. 회원가입");
		System.out.println("2. 로그인");
		System.out.println("0. 종료");
	}
	
	private void printLoginMenu() {
		System.out.printf("====== %s ======\n",this.name);
		System.out.printf("  %s님 환영합니다!!!\n",this.um.getUser(this.log).getName());
		System.out.println("1. 입급");
		System.out.println("2. 출금");
		System.out.println("3. 이체");
		System.out.println("4. 조회");
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
			this.fm.saveUserFile(user);
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
			this.am.removeAccountById(user.getId(), user.getAccSize());
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
		
		if(user.getAccSize() < Account.LIMIT) {
			this.am.addAcc(acc);
			this.um.addUserAcc(this.log, acc);
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
			
			if(sel < user.getAccSize()) {
				for(int i=0; i<this.am.getAllAccSize(); i++)
					if(user.getAcc(sel).getAccNum().equals(this.am.getAcc(i).getAccNum()))
						idx = i;
				
				this.am.removeAcc(idx);
				this.um.removeUserAcc(user.getId(), sel);
				System.out.println("\n...계좌 철회 완료\n");
			}
			else
				System.out.println("\n번호를 확인하세요.\n");
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
			System.out.println("\n보유중인 계좌가 없습니다.\n");
			return false;
		}
	}
	
	private void deposit() {
		User user = this.um.getUser(this.log);
		if(printMyAcc()) {
			System.out.print("계좌 선택 : ");
			int sel = this.scan.nextInt()-1;
			
			if(sel < user.getAccSize()) {
				System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
				System.out.print("입금 금액 : ");
				int money = this.scan.nextInt();
				
				if(money > 0) {
					this.um.setUserAccMoney(user.getId(), sel, money);
					this.am.setAccMoney(sel, money);
					System.out.println("\n입금 완료.");
					System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
				}
			}
		}
	}
	
	private void withdraw() {
		User user = this.um.getUser(this.log);
		if(printMyAcc()) {
			System.out.print("계좌 선택 : ");
			int sel = this.scan.nextInt()-1;
			
			if(sel < user.getAccSize()) {
				System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
				System.out.print("출금 금액 : ");
				int money = this.scan.nextInt();
				
				int curMoney = user.getAcc(sel).getMoney();
				
				if(money <= curMoney) {
					this.um.setUserAccMoney(user.getId(), sel, curMoney - money);
					this.am.setAccMoney(sel, curMoney - money);
					System.out.println("\n출금 완료.");
					System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
				}
				else
					System.out.println("\n잔액이 부족합니다.\n");
			}
		}
	}
	
	private void transfer() {
		User user = this.um.getUser(this.log);
		if(printMyAcc()) {
			System.out.print("계좌 선택 : ");
			int sel = this.scan.nextInt()-1;
			
			if(sel < user.getAccSize()) {
				System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
				System.out.print("이체할 계좌번호 : ");
				String transferedAccNum = this.scan.next();
				
				boolean checkTransfer = false;
				for(int i=0; i<this.am.getAllAccSize(); i++) {
					if(this.am.getAcc(i).getAccNum().equals(transferedAccNum)) {
						checkTransfer = true;
						
						User transferedUser = this.um.getUserById(this.am.getAcc(i).getId());
						
						System.out.printf("이체 계좌번호 예금주명 : %s\n",transferedUser.getName());
						
						System.out.print("이체 금액 : ");
						int money = this.scan.nextInt();
						
						int curMoney = user.getAcc(sel).getMoney();
						int transferedCurMoney = this.am.getAcc(i).getMoney();
						
						
						if(money <= curMoney) {
							this.um.setUserAccMoney(user.getId(), sel, curMoney - money);
							this.um.setUserAccMoney(transferedUser.getId(), transferedAccNum, transferedCurMoney + money);
							this.am.setAccMoney(sel, curMoney - money);
							this.am.setAccMoney(this.am.indexOfByAccNum(transferedAccNum), transferedCurMoney + money);
							System.out.println("\n이체 완료.");
							System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
						}
						else
							System.out.println("\n잔액이 부족합니다.\n");
					}
				}
				if(!checkTransfer)
					System.out.println("\n없는 계좌번호입니다.\n");
			}
		}
	}
	
	private void checkMyInfo() {
		
		System.out.printf("\n이름 : %s\nID : %s\n보유 계좌 : \n",this.um.getUser(this.log).getName(), this.um.getUser(this.log).getId());
		if(printMyAcc()) {
			checkMyAccMoney();
		}
	}
	
	private void checkMyAccMoney() {
		User user = this.um.getUser(this.log);
		System.out.print("잔액 조회(번호) : ");
		int sel = this.scan.nextInt()-1;
		
		if(sel < user.getAccSize()) {
			System.out.printf("\n계좌 잔액 : %d원\n\n",user.getAcc(sel).getMoney());
		}
	}
	
	
	public void run() {
		if(this.fm.loadUserFile() != null && this.fm.loadAccFile() != null) {
			this.um.setList(this.fm.loadUserFile());
			this.am.setList(this.fm.loadAccFile());
			for(int i=0; i<this.am.getAllAccSize(); i++) {
				for(int j=0; j<this.um.getUserSize(); j++) {
					if(this.am.getAcc(i).getId().equals(this.um.getUser(j).getId())) {
						Account acc = new Account(this.am.getAcc(i).getId(), this.am.getAcc(i).getAccNum(), this.am.getAcc(i).getMoney());
						this.um.setUserAcc(this.um.getUser(j), acc);
					}
				}
			}
		}
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
