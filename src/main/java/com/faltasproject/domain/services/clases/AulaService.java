package com.faltasproject.domain.services.clases;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.persistance_ports.clases.AulaPersistance;

@Service
public class AulaService {
	
	private final AulaPersistance aulaPersistance;
	
	public AulaService(AulaPersistance aulaPersistance) {
		this.aulaPersistance=aulaPersistance;
	}
	
	
	public Aula create(Aula aula) {
		return aulaPersistance.create(aula);
	}
}
