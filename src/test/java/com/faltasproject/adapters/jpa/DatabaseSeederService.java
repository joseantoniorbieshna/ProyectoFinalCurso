package com.faltasproject.adapters.jpa;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.ClasesSeederService;
import com.faltasproject.adapters.jpa.horario.HorarioSeederService;

@Service
public class DatabaseSeederService {
	
	private final ClasesSeederService clasesSeederService;
	private final HorarioSeederService horarioSeederService;
	
	public DatabaseSeederService(ClasesSeederService clasesSeederService,HorarioSeederService horarioSeederService) {
		this.clasesSeederService=clasesSeederService;
		this.horarioSeederService=horarioSeederService;
		
		this.setDatabases();
	}
	
	public void setDatabases(){
		this.clasesSeederService.seedDatabase();
		this.horarioSeederService.seedDatabase();
	}
	
	public void deleteAll() {
		this.clasesSeederService.deleteAll();
		this.horarioSeederService.deleteAll();
	}
	
	public void reSeedDatabase() {
		this.deleteAll();
		this.setDatabases();
	}
}
