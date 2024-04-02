package com.faltasproject.adapters.jpa;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.*;
import com.faltasproject.adapters.jpa.horario.HorarioSeederService;
import com.faltasproject.adapters.jpa.profesorado.ProfesoradoSeederService;

@Service
public class DatabaseSeederService {
	
	private final ClasesSeederService clasesSeederService;
	private final HorarioSeederService horarioSeederService;
	private final ProfesoradoSeederService profesoradoSeederService;
	
	public DatabaseSeederService(ClasesSeederService clasesSeederService,
			HorarioSeederService horarioSeederService,
			ProfesoradoSeederService profesoradoSeederService) {
		
		this.clasesSeederService=clasesSeederService;
		this.horarioSeederService=horarioSeederService;
		this.profesoradoSeederService=profesoradoSeederService;
		
		this.reSeedDatabase();
	}
	
	public void setDatabases(){
		this.profesoradoSeederService.seedDatabase();
		this.clasesSeederService.seedDatabase();
		this.horarioSeederService.seedDatabase();
	}
	
	public void deleteAll() {
		this.horarioSeederService.deleteAll();
		this.profesoradoSeederService.deleteAll();
		this.clasesSeederService.deleteAll();
	}
	
	public void reSeedDatabase() {
		this.deleteAll();
		this.setDatabases();
	}
}
