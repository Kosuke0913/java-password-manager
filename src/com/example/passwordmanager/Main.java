package com.example.passwordmanager;

import java.util.List;
import java.util.Scanner;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.service.PasswordManager;
import com.example.passwordmanager.util.FileManager;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		PasswordManager manager = new PasswordManager();
		FileManager fileManager = new FileManager();
		
		fileManager.load(manager);
		
		int menu = -1;
		
		while(true) {
			System.out.println("==== パスワード管理ツール ====");
			System.out.println("1. 登録");
			System.out.println("2. 一覧表示");
			System.out.println("3. 検索");
			System.out.println("4. 更新");
			System.out.println("5. 削除");
			System.out.println("6. 終了");
			System.out.println("選択してください:");
			
			try {	
				menu = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("数字(1-6)を入力してください");
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
				fileManager.save(manager.getEntries());
				System.out.println("終了します。");
				scanner.close();
				return;
			
			default:
				System.out.println("1-6を入力してください。");
			}
			
			System.out.println();
		}
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
			displayEntry(entry);
		}
	}
	
	private static void searchPassword(Scanner scanner, PasswordManager manager) {
		
		System.out.print("検索するサービス名: ");
		String serviceName = scanner.nextLine();
		
		PasswordEntry result = manager.searchByServiceName(serviceName);
		
		if(result != null) {
			displayEntry(result);
		} else {
			System.out.println("見つかりませんでした。");
		}
	}
	
	private static void displayEntry(PasswordEntry entry) {
		System.out.println("-------------------------");
		System.out.println("サービス名:" + entry.getServiceName());
		System.out.println("ユーザー名" + entry.getUserName());
		System.out.println("パスワード" + entry.getPassword());
	}

	private static void deletePassword(Scanner scanner, PasswordManager manager) {
	
		System.out.print("削除するサービス名: ");
		String serviceName = scanner.nextLine();
		
		PasswordEntry result = manager.searchByServiceName(serviceName);
		
		if(result != null) {
			displayEntry(result);
			
			System.out.println("本当に削除しますか？(y/n)");
			String answer = scanner.nextLine();
			
			if(answer.equalsIgnoreCase("y")){
				manager.removeEntry(serviceName);
				System.out.println("削除しました。");
			} else {
				System.out.println("削除をキャンセルしました。");
			}
		} else {
			System.out.println("見つかりませんでした。");
		}
	}
	
	private static void updatePassword(Scanner scanner, PasswordManager manager) {
		System.out.print("更新するサービス名: ");
		String serviceName = scanner.nextLine();
		
		PasswordEntry entry = manager.searchByServiceName(serviceName);
		
		if(entry == null) {
			System.out.println("見つかりませんでした。");
			return;
		}
		
		displayEntry(entry);
		
		System.out.print("更新するユーザー名: ");
		String newUserName = scanner.nextLine();
		
		System.out.print("更新するパスワード: ");
		String newPassword = scanner.nextLine();
		
		boolean updated = manager.updateEntry(serviceName, newUserName, newPassword);
		
		if(updated) {
			System.out.println("更新しました。");
		}
	}
}
