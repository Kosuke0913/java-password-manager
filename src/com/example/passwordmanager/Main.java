package com.example.passwordmanager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.service.PasswordManager;
import com.example.passwordmanager.util.FileManager;
import com.example.passwordmanager.util.MasterPasswordManager;
import com.example.passwordmanager.util.PasswordGenerator;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		PasswordManager manager = new PasswordManager();
		FileManager fileManager = new FileManager();
		PasswordGenerator generator = new PasswordGenerator();
		MasterPasswordManager masterManager = new MasterPasswordManager();
		
		if(!masterManager.exists()) {
			System.out.println("マスターパスワードが設定されていません。");
			String password = inputNewMasterPassword(scanner);
			masterManager.save(password);
			System.out.println("設定しました。");
		} else {
			if(!authenticateMasterPassword(scanner, masterManager)) {
				scanner.close();
				return;
			}
		}
		
		fileManager.load(manager);
		
		int menu = -1;
		
		while(true) {
			displayMenu();
			
			try {	
				menu = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("数字(1-9)を入力してください");
				continue;
			}
			
			switch(menu) {
			case 1:
				registerPassword(scanner, manager);
				break;
				
			case 2: 
				displayEntries(manager);
				break;
			
			case 3: 
				searchPassword(scanner, manager);
				break;
				
			case 4: 
				updatePassword(scanner, manager);
				break;
				
			case 5:
				deletePassword(scanner, manager);
				break;
				
			case 6:
				displayServices(manager);
				break;
				
			case 7:
				generatePassword(scanner, generator, manager);
				break;
				
			case 8:
				changeMasterPassword(scanner, masterManager);
				break;
			
			case 9:
				fileManager.save(manager.getEntries());
				System.out.println("終了します。");
				scanner.close();
				return;
			
			default:
				System.out.println("1-9を入力してください。");
			}
			
			System.out.println();
		}
	}
	
	private static boolean authenticateMasterPassword(Scanner scanner, MasterPasswordManager masterManager) {
		int count = 3;
		while(true) {
			System.out.print("マスターパスワード: ");
			String masterPassword = scanner.nextLine();
			
			if(masterManager.authenticate(masterPassword)) {
				System.out.println("認証成功");
				System.out.println();
				return true;
			} 
			count--;
			System.out.println("認証失敗（残り" + count +"回）");
			if(count == 0) {
				scanner.close();
				return false;
			}
		}
	}
	
	private static String inputNewMasterPassword(Scanner scanner) {
		while(true) {
			System.out.print("新しいマスターパスワード: ");
			String password = scanner.nextLine();
			System.out.println();
			
			System.out.println("確認のためもう一度入力してください。");
			String confirmPassword = scanner.nextLine();
			if(password.equals(confirmPassword)) {
				return password;
			} 
			System.out.println("一致しません。");
		}
	}
	
	private static void changeMasterPassword(Scanner scanner, MasterPasswordManager masterManager) {
		if(authenticateMasterPassword(scanner, masterManager)){
			String password = inputNewMasterPassword(scanner);
			masterManager.save(password);
			System.out.println("変更しました。"); 
		} 
	}
	
	private static void displayMenu() {
		System.out.println("==== パスワード管理ツール ====");
		System.out.println("1. 登録");
		System.out.println("2. 一覧表示");
		System.out.println("3. 検索");
		System.out.println("4. 更新");
		System.out.println("5. 削除");
		System.out.println("6. サービス一覧");
		System.out.println("7. パスワード生成");
		System.out.println("8. マスターパスワード変更");
		System.out.println("9. 終了");
		System.out.println("選択してください:");
	}
	
	private static void registerPassword(Scanner scanner, PasswordManager manager) {
		
		System.out.println("サービス名:");
		String serviceName = scanner.nextLine();
		
		System.out.println("ユーザー名:");
		String userName = scanner.nextLine();
		
		System.out.println("パスワード:");
		String password = scanner.nextLine();
		
		PasswordEntry entry = new PasswordEntry(
			serviceName,
			userName,
			password);
		
		manager.addEntry(entry);
		
		System.out.println("登録しました!");
	}
	
	private static void displayEntries(PasswordManager manager) {
		List<PasswordEntry> entries = manager.getEntries();
		
		if(entries.isEmpty()) {
			System.out.println("登録されているデータはありません。");
			return;
		}
		for(PasswordEntry entry : entries){
			displayEntry(entry, false);
		}
	}
	
	private static void searchPassword(Scanner scanner, PasswordManager manager) {
		
		System.out.print("検索するサービス名: ");
		String serviceName = scanner.nextLine();
		
		List<PasswordEntry> result = manager.searchByServiceName(serviceName);
		
		if(result.isEmpty()) {
			System.out.println("見つかりませんでした。");
			return;
		}
		for(PasswordEntry entry : result) {
			displayEntry(entry, true);
		}
	}
	
	private static void displayEntry(PasswordEntry entry, boolean showPassword) {
		System.out.println("-------------------------");
		System.out.println("サービス名:" + entry.getServiceName());
		System.out.println("ユーザー名:" + entry.getUserName());
		
		if(showPassword) {
			System.out.println("パスワード:" + entry.getPassword());
		} else {
			System.out.println("パスワード:********");
		}
	}

	private static void deletePassword(Scanner scanner, PasswordManager manager) {
	
		System.out.print("削除するサービス名: ");
		String serviceName = scanner.nextLine();
		
		List<PasswordEntry> result = manager.searchByServiceName(serviceName);
		
		PasswordEntry selected = selectEntry(scanner, result);
		
		if(selected == null) {
			return;
		}
		
		System.out.println("本当に削除しますか？(y/n)");
		String answer = scanner.nextLine();
		
		if(answer.equalsIgnoreCase("y")){
			manager.removeEntry(selected);
			System.out.println("削除しました。");
		} else {
			System.out.println("削除をキャンセルしました。");
		}
	}
	
	private static void updatePassword(Scanner scanner, PasswordManager manager) {
		System.out.print("更新するサービス名: ");
		String serviceName = scanner.nextLine();
		
		List<PasswordEntry> result = manager.searchByServiceName(serviceName);
		
		PasswordEntry selected = selectEntry(scanner, result);
		
		if (selected == null) {
		    return;
		}
		
		System.out.print("更新するユーザー名: ");
		String newUserName = scanner.nextLine();
		
		System.out.print("更新するパスワード: ");
		String newPassword = scanner.nextLine();
		
		manager.updateEntry(selected, newUserName, newPassword);
		System.out.println("更新しました。");
	}
	
	private static PasswordEntry selectEntry(Scanner scanner, List<PasswordEntry> result) {
		
		if(result.isEmpty()) {
			System.out.println("見つかりませんでした。");
			return null;
		}
		
		for(int i = 0; i < result.size(); i++) {
			System.out.println("[" + (i + 1) + "]");
			displayEntry(result.get(i), false);
			System.out.println();
		}
		
		System.out.print("番号を選択してください: ");
		int number = scanner.nextInt();
		scanner.nextLine();
		if(number < 1 || number > result.size()) {
			System.out.println("番号が正しくありません。");
			return null;
		}
		
		PasswordEntry selected = result.get(number-1);
		
		System.out.println("選ばれたエントリー:");
		displayEntry(selected, false);
		
		return selected;
	}
	
	private static void displayServices(PasswordManager manager) {
		Map<String, Integer> serviceCounts = manager.getServiceCounts();
		
		if(serviceCounts.isEmpty()) {
			System.out.println("登録されているデータはありません。");
			return;
		}
		System.out.println("==== 登録されているサービス ====");
		for(Map.Entry<String, Integer> entry : serviceCounts.entrySet()) {
			System.out.println( entry.getKey() + " (" + entry.getValue() + "件)");
		}
	}
	
	private static void generatePassword(Scanner scanner, PasswordGenerator generator, PasswordManager manager){
		
		try {
			System.out.print("パスワードの長さ: ");
			int length = Integer.parseInt(scanner.nextLine());
			if(length < 6) {
				System.out.println("6以上を入力してください。");
				return;
			}
			String password = generator.generate(length);
			
			System.out.println("生成されたパスワード:");
			System.out.println(password);
			System.out.print("このパスワードで登録しますか？(y/n): ");
			String answer = scanner.nextLine();
			
			if(answer.equalsIgnoreCase("y")) {
				System.out.println("サービス名:");
				String serviceName = scanner.nextLine();
				
				System.out.println("ユーザー名:");
				String userName = scanner.nextLine();
				
				PasswordEntry entry = new PasswordEntry(
						serviceName,
						userName,
						password);
					
					manager.addEntry(entry);
					
					System.out.println("登録しました!");
			}
			else {
				System.out.println("登録をキャンセルしました。");

			}
		} catch (NumberFormatException e) {
			System.out.println("数字を入力してください");
		}
	}
}
