package com.example.passwordmanager;

import java.util.Scanner;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.service.PasswordManager;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		PasswordManager manager = new PasswordManager();
		while(true) {
			System.out.println("==== パスワード管理ツール ====");
			System.out.println("1. 登録");
			System.out.println("2. 一覧表示");
			System.out.println("3. 検索");
			System.out.println("4. 終了");
			System.out.println("選択してください:");
			
			int menu = scanner.nextInt();
			
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
				System.out.println("終了します。");
				scanner.close();
				return;
			
			default:
				System.out.println("1-4を入力してください。");
			}
			
			System.out.println();
		}
	}
	
	private static void registerPassword(Scanner scanner, PasswordManager manager) {
		scanner.nextLine();//改行
		
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
		for(PasswordEntry entry : manager.getEntries()) {
			displayEntry(entry);
		}
	}
	
	private static void searchPassword(Scanner scanner, PasswordManager manager) {
		scanner.nextLine();
		
		System.out.println("検索するサービス名");
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
}
