package com.example.passwordmanager.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.service.PasswordManager;

public class FileManager {
	
	public void save(List<PasswordEntry> entries) {
		try(FileWriter writer = new FileWriter("passwords.csv")){
			for(PasswordEntry entry : entries) {
				writer.write(entry.getServiceName() + "," + 
							entry.getUserName() + "," + 
							entry.getPassword() + "\n"
							);
			}
			System.out.println("保存しました。");
		} catch(IOException e) {
			System.out.println("保存に失敗しました。");
		}
	}
	
	public void load(PasswordManager manager) {
		try(BufferedReader reader = new BufferedReader(new FileReader("passwords.csv"))){
			String line;
			while( (line = reader.readLine()) != null) {
				if(line.isBlank()) {
					continue;
				}
				String[] data = line.split(",");
				PasswordEntry entry = new PasswordEntry(
						data[0], data[1], data[2]
						);
				manager.addEntry(entry);
			}
		} catch (IOException e) {
			System.out.println("保存データがありません。");
		}
	}
}
