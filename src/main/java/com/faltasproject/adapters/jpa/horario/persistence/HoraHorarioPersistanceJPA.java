package com.faltasproject.adapters.jpa.horario.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.adapters.jpa.clases.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;
import com.faltasproject.adapters.jpa.horario.daos.HoraHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.key_compound.TramoHorarioKey;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.persistance_ports.horario.HoraHorarioPersistance;

import jakarta.transaction.Transactional;

@Repository("horaHorarioPersistance")
public class HoraHorarioPersistanceJPA implements HoraHorarioPersistance{
	
	private static final String MESSAGE_EXIST = "La hora del horario no existe.";
	private static final String MESSAGE_NOT_EXIST = "La hora del horario no existe.";

	private final HoraHorarioRepositoryJPA horaHorarioRepositoryJPA;
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	private final SesionRepositoryJPA sesionRepositoryJPA;
	
	
	public HoraHorarioPersistanceJPA(HoraHorarioRepositoryJPA horaHorarioRepositoryJPA,
			TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA, SesionRepositoryJPA sesionRepositoryJPA) {
		super();
		this.horaHorarioRepositoryJPA = horaHorarioRepositoryJPA;
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
		this.sesionRepositoryJPA = sesionRepositoryJPA;
	}

	@Override
	public HoraHorario create(HoraHorario horaHorario) {
		IdTramoHorarioDTO idTramoHorario = new IdTramoHorarioDTO(horaHorario.getDiaTramoHorario(), horaHorario.getIndiceTramoHorario());
		
		assertReferenciaTramoHorarioExist(idTramoHorario);
		assertReferenciaSesionExist(horaHorario.getReferenciaSesion());
		
		if(existDiaIndiceTramoHorarioAndReferenciaSesion(horaHorario.getReferenciaSesion(), idTramoHorario)) {
			throw new ConflictException(MESSAGE_EXIST);
			
		}
		HoraHorarioEntity horaHorarioPersist = new HoraHorarioEntity(horaHorario);
		persistData(horaHorarioPersist);
		
		return horaHorarioRepositoryJPA.save(horaHorarioPersist).toHoraHorario();
	}



	@Override
	public HoraHorario update(String referenciaSesion,int dia, int indice,HoraHorario horaHorarioUpdate) {
		IdTramoHorarioDTO idTramoHorario = new IdTramoHorarioDTO(dia, indice);
		
		assertReferenciaTramoHorarioExist(idTramoHorario);
		assertReferenciaSesionExist(referenciaSesion);
		
		HoraHorarioEntity horaHorarioEntity = horaHorarioRepositoryJPA.findByReferenciaAndTramoHorario(referenciaSesion, dia, indice)
				.orElseThrow( ()-> new NotFoundException(MESSAGE_NOT_EXIST));
		
		if( (!referenciaSesion.equals(horaHorarioUpdate.getReferenciaSesion()) || dia!=horaHorarioUpdate.getDiaTramoHorario() || indice!=horaHorarioUpdate.getIndiceTramoHorario())
				&& existDiaIndiceTramoHorarioAndReferenciaSesion(horaHorarioUpdate.getReferenciaSesion(), new IdTramoHorarioDTO(horaHorarioUpdate.getDiaTramoHorario(),horaHorarioUpdate.getIndiceTramoHorario()))) {
			throw new ConflictException(  String.format("La horaHorario con la referencia de sesion '%s' dia '%d' e indice '%d' a la que quiere actualizar ya existe.", horaHorarioUpdate.getReferenciaSesion(),horaHorarioUpdate.getDiaTramoHorario(),horaHorarioUpdate.getIndiceTramoHorario())  );
		}
		
		horaHorarioEntity.fromHoraHorario(horaHorarioUpdate);
		persistData(horaHorarioEntity);
		
		return horaHorarioRepositoryJPA.save(horaHorarioEntity).toHoraHorario();
	}

	@Override
	public Stream<HoraHorario> readAll() {
		return horaHorarioRepositoryJPA.findAll().stream().map(HoraHorarioEntity::toHoraHorario);
	}

	@Override
	public Stream<HoraHorario> readAllByReferenciaProfesor(String referenciaProfesor) {
		return horaHorarioRepositoryJPA.findAllByProfesorFaltante(referenciaProfesor).stream()
				.map(HoraHorarioEntity::toHoraHorario);
	}

	@Override
	public Stream<HoraHorario> readAllByReferenciaSesion(String referenciaSesion) {
		return horaHorarioRepositoryJPA.findAllByReferenciaSesion(referenciaSesion).stream().map(HoraHorarioEntity::toHoraHorario);
	}

	@Override
	public Stream<HoraHorario> readAllByIdTramoHorario(IdTramoHorarioDTO referenciaTramoHorario) {
		return horaHorarioRepositoryJPA.findAllByTramoHorario(referenciaTramoHorario.getDia(), referenciaTramoHorario.getIndice())
				.stream()
				.map(HoraHorarioEntity::toHoraHorario);
	}

	@Override
	public HoraHorario readByReferenciaProfesorAndTramoHorario(String referenciaProfesor,
			IdTramoHorarioDTO referenciaTramoHorario) {
		return horaHorarioRepositoryJPA.findByProfesorFaltanteAndTramoHorario(referenciaProfesor, referenciaTramoHorario.getDia(), referenciaTramoHorario.getIndice())
				.orElseThrow(()-> new NotFoundException(String.format("La hora de horario con la referencia del profesor %s y el dia %d y el indice %d no existe.", referenciaProfesor,referenciaTramoHorario.getDia(),referenciaTramoHorario.getIndice())))
				.toHoraHorario();
	}

	@Override
	public HoraHorario readByReferenciaSesionAndTramoHorario(String referenciaSesion,
			IdTramoHorarioDTO idTramoHorarioDTO) {
		return horaHorarioRepositoryJPA
				.findByReferenciaAndTramoHorario(referenciaSesion, idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice())
				.orElseThrow( ()-> new NotFoundException(MESSAGE_NOT_EXIST))
				.toHoraHorario();
	}

	@Override
	public boolean deleteByReferenciaSesionAndTramoHorario(String referenciaSesion,
			IdTramoHorarioDTO idTramoHorarioDTO) {
		
		assertReferenciaTramoHorarioExist(idTramoHorarioDTO);
		assertReferenciaSesionExist(referenciaSesion);
		
		HoraHorarioEntity horaHorarioEntity = horaHorarioRepositoryJPA.findByReferenciaAndTramoHorario(referenciaSesion, idTramoHorarioDTO.getDia(), idTramoHorarioDTO.getIndice())
				.orElseThrow( ()-> new NotFoundException(MESSAGE_NOT_EXIST));
		
		horaHorarioRepositoryJPA.delete(horaHorarioEntity);

		return !existDiaIndiceTramoHorarioAndReferenciaSesion(referenciaSesion, idTramoHorarioDTO);
	}

	@Override
	public boolean existDiaIndiceTramoHorarioAndReferenciaSesion(String referenciaSesion,IdTramoHorarioDTO idTramoHorarioDTO) {
		return horaHorarioRepositoryJPA.findByReferenciaAndTramoHorario(referenciaSesion,idTramoHorarioDTO.getDia(),idTramoHorarioDTO.getIndice())
				.isPresent();
	}
	
	private void assertReferenciaTramoHorarioExist(IdTramoHorarioDTO idTramoHorario) {
		Optional<TramoHorarioEntity>tramoHorarioEntity = tramoHorarioRepositoryJPA.findById(new TramoHorarioKey(idTramoHorario.getDia(),idTramoHorario.getIndice()));
		if(!tramoHorarioEntity.isPresent()){
			throw new  NotFoundException("El tramo con el id "+idTramoHorario.getDia()+" e indice "+idTramoHorario.getIndice()+" no existe");
		}
	}

	private void assertReferenciaSesionExist(String referencia) {
		Optional<SesionEntity> sesion=sesionRepositoryJPA.findByReferencia(referencia);
		if(!sesion.isPresent()) {
			throw new NotFoundException("La sesion con la referencia "+referencia+" no existe");
		}
	}
	
	private void persistData(HoraHorarioEntity horaHorarioPersist) {
		TramoHorarioEntity tramoHorarioPersist = tramoHorarioRepositoryJPA.findById( new TramoHorarioKey(horaHorarioPersist.getDiaTramoHorario(), horaHorarioPersist.getIndiceTramoHorario()) )
				.orElseThrow(()-> new NotFoundException(String.format("El tramo horario con el dia %d y el indice %d no existe.", horaHorarioPersist.getDiaTramoHorario(),horaHorarioPersist.getIndiceTramoHorario())));
		SesionEntity sesionPersist = sesionRepositoryJPA.findByReferencia(horaHorarioPersist.getReferenciaSesion())
				.orElseThrow(()-> new NotFoundException( String.format("La sesion co la referencia %s no existe.", horaHorarioPersist.getReferenciaSesion())) );
		
		horaHorarioPersist.setSesion(sesionPersist);
		horaHorarioPersist.setTramoHorario(tramoHorarioPersist);
	}
	
	
	
}
