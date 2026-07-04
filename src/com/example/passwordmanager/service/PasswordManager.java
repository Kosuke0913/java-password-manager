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
		return entries;
	}
	
	public PasswordEntry searchByServiceName(String serviceName) {
		for(PasswordEntry entry : entries) {
			if(entry.getServiceName().equalsIgnoreCase(serviceName)) {
				return entry;
			}
		}
		return null;
	}
	
	public boolean removeEntry(String serviceName) {
		for(PasswordEntry entry : entries) {
			if(entry.getServiceName().equalsIgnoreCase(serviceName)) {
				entries.remove(entry);
				return true;
			}
		}
		return false;
	}
	
}
