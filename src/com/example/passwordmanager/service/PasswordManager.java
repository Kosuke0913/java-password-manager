package com.example.passwordmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.example.passwordmanager.model.PasswordEntry;

public class PasswordManager {
	private List<PasswordEntry> entries = new ArrayList<>();
	
	public void addEntry(PasswordEntry entry) {
		entries.add(entry);
	}
	
	public List<PasswordEntry> getEntries(){
		return new ArrayList<>(entries);
	}
	
	public List<PasswordEntry> searchByServiceName(String serviceName) {
		List<PasswordEntry> result = new ArrayList<>();
		for(PasswordEntry entry : entries) {
			if(entry.getServiceName().equalsIgnoreCase(serviceName)) {
				result.add(entry);
			}
		}
		return result;
	}
	
	public boolean removeEntry(PasswordEntry entry) {
		return entries.remove(entry);
	}
	
	public void updateEntry(PasswordEntry entry, String newUserName, String newPassword) {
		entry.setUserName(newUserName);
		entry.setPassword(newPassword);
	}
}
