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
			case 1: {
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
				break;
			}
				
			case 2: {
				
				for(PasswordEntry entry : manager.getEntries()) {
					System.out.println("-------------------------");
					System.out.println("サービス名:" + entry.getServiceName());
					System.out.println("ユーザー名" + entry.getUserName());
					System.out.println("パスワード" + entry.getPassword());
				}
				
				break;
			}
			
			case 3: {
				scanner.nextLine();
				
				System.out.println("検索するサービス名");
				String serviceName = scanner.nextLine();
				
				PasswordEntry result = manager.searchByServiceName(serviceName);
				
				if(result != null) {
					System.out.println("-------------------------");
					System.out.println("サービス名:" + result.getServiceName());
					System.out.println("ユーザー名" + result.getUserName());
					System.out.println("パスワード" + result.getPassword());
				} else {
					System.out.println("見つかりませんでした。");
				}
				break;
			}
			
			case 4:
				System.out.println("終了します。");
				scanner.close();
				return;
			
			default:
				System.out.println("1-3を入力してください。");
			}
			
			System.out.println();
		}
	}
}
