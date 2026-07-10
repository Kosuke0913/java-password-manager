package com.example.passwordmanager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MasterPasswordManager {
	
	public boolean exists() {
		File file = new File("master.txt");
		return file.exists();
	}
	
	public void save(String password) {
		try (FileWriter writer = new FileWriter("master.txt")){
			writer.write(password);
			System.out.println("保存しました。");
		} catch (IOException e) {
			System.out.println("保存に失敗しました。");
		}
	}
	
	public String load() {
		try(BufferedReader reader = new BufferedReader(new FileReader("master.txt"))){
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("保存データがありません。");
			return null;
		}
	}
}
