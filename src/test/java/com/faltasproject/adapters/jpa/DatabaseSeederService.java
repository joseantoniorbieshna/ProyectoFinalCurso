package com.faltasproject.adapters.jpa;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.ClasesSeederService;

@Service
public class DatabaseSeederService {
	
	private ClasesSeederService clasesSeederService;
	
	public DatabaseSeederService(ClasesSeederService clasesSeederService) {
		this.clasesSeederService=clasesSeederService;
		
		this.setDatabases();
	}
	
	private void setDatabases(){
		this.clasesSeederService.seedDatabase();
	}
	
	private void deleteAll() {
		this.clasesSeederService.deleteAll();
	}
	
	private void reSeedDatabase() {
		this.deleteAll();
		this.setDatabases();
	}
}
