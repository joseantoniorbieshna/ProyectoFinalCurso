package com.faltasproject.domain.services.horario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.profesorado.persistence.ProfesorPersistanceJPA;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateByDiaProfesorDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaDeleteInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaSustituirInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaUpdateInputDTO;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.models.horario.mappers.FaltaIdMapper;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;
import com.faltasproject.domain.persistance_ports.horario.FaltaPersistance;
import com.faltasproject.domain.persistance_ports.horario.HoraHorarioPersistance;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;
import com.faltasproject.domain.persistance_ports.profesorado.ProfesorPersistance;
import com.faltasproject.security.usuarios.dtos.UserInfo;

@Service
public class FaltaService {

	private FaltaPersistance faltaPersistance;
	private HoraHorarioPersistance horaHorarioPersistance;
	private TramoHorarioPersistance tramoPersistance;
	private SesionPersistance sesionPersistance;
	private ProfesorPersistance profesorPersistance;

	public FaltaService(FaltaPersistance faltaPersistance, TramoHorarioPersistance tramoPersistance,
			SesionPersistance sesionPersistance, ProfesorPersistance profesorPersistance,
			HoraHorarioPersistance horaHorarioPersistance) {
		super();
		this.faltaPersistance = faltaPersistance;
		this.tramoPersistance = tramoPersistance;
		this.sesionPersistance = sesionPersistance;
		this.profesorPersistance = profesorPersistance;
		this.horaHorarioPersistance=horaHorarioPersistance;
	}

	public Falta create(FaltaCreateInputDTO faltaCreateInputDTO, UserInfo userInfoDTO) {

		if (isUser(userInfoDTO)) {
			assertDayIsTodayOrLater(faltaCreateInputDTO.getFecha());
		}

		TramoHorario tramoHorario = tramoPersistance
				.readById(new IdTramoHorarioDTO(faltaCreateInputDTO.getDia(), faltaCreateInputDTO.getIndice()));
		Sesion sesion = sesionPersistance.readByReferencia(faltaCreateInputDTO.getReferenciaSesion());
		HoraHorario horaHorario = new HoraHorario(sesion, tramoHorario);

		Falta falta = new Falta(horaHorario, faltaCreateInputDTO.getComentario().orElse(""),
				faltaCreateInputDTO.getFecha());
		return this.faltaPersistance.create(falta);
	}

	public List<Falta> createAll(FaltaCreateByDiaProfesorDTO faltaCreateAllInput, UserInfo userInfoDTO) {

		if (isUser(userInfoDTO)) {
			assertDayIsTodayOrLater(faltaCreateAllInput.getFecha());
			if (!faltaCreateAllInput.getReferenciaProfesor().equals(userInfoDTO.referenciaProfesor())) {
				throw new ConflictException("No tienes permiso para realizar esta acción");
			}
		}

		Stream<HoraHorario> allByReferenciaProfesor = horaHorarioPersistance.readAllByReferenciaProfesor(faltaCreateAllInput.getReferenciaProfesor());
		List<Falta> faltas = allByReferenciaProfesor
		.filter(horaHorario->horaHorario.getDiaTramoHorario()==faltaCreateAllInput.getDia())
		.map(horaHorario->
			new Falta(horaHorario, faltaCreateAllInput.getComentario().orElse(""), faltaCreateAllInput.getFecha())
		).toList();
		
		List<Falta> faltasSave = faltaPersistance.createAll(faltas).toList();
		if(faltasSave.isEmpty()) {
			throw new ConflictException("Ya se han creado todas las faltas posibles para este día y fecha");
		}
		return faltasSave;
	}

	public List<Falta> findAll() {
		return this.faltaPersistance.readAll().toList();
	}

	public List<Falta> findAllTodayAndLaterByIdAndIndice(int dia, int indice) {
		return this.faltaPersistance.readAll()
				.filter(t -> t.getDiaTramoHorario() == dia && t.getIndiceTramoHorario() == indice
						&& (t.getFecha().isEqual(LocalDate.now()) || t.getFecha().isAfter(LocalDate.now())))
				.toList();
	}

	public List<Falta> findAllByFaltaBetween(LocalDate fechaInicio, LocalDate fechaFin) {
		if (fechaInicio.isAfter(fechaFin)) {
			throw new ConflictException("La fecha de inicio está después de la de fin");
		}

		return this.faltaPersistance.readFaltasBetweenFechas(fechaInicio, fechaFin).toList();
	}

	public int countTotalFaltasSustituidasByReferenciaProfesorDiaAndIndice(String referenciaProfesor,int dia,int indice) {
		return (int) this.faltaPersistance.readAll()
				.filter(t -> t.getProfesorSustituto().isPresent() && t.getReferenciaProfesorSustituto().equals(referenciaProfesor) && t.getDiaTramoHorario()==dia && t.getIndiceTramoHorario()==indice)
				.count();
	}
	public Falta update(FaltaUpdateInputDTO faltaUpdateInputDTO, UserInfo userInfoDTO) {

		IdFaltaDTO idFaltaActual = new IdFaltaDTO(faltaUpdateInputDTO.getReferenciaSesion(),
				faltaUpdateInputDTO.getDia(), faltaUpdateInputDTO.getIndice(), faltaUpdateInputDTO.getFecha());

		if (isUser(userInfoDTO)) {
			assertDayIsTodayOrLater(faltaUpdateInputDTO.getFecha());
			assertDayIsTodayOrLater(faltaUpdateInputDTO.getFechaNueva());

			assertUserIspropietario(idFaltaActual, userInfoDTO);
		}

		Falta faltaActualizar = faltaPersistance.readById(idFaltaActual);
		faltaActualizar.setComentario(faltaUpdateInputDTO.getComentario().orElse(""));
		faltaActualizar.setFecha(faltaUpdateInputDTO.getFechaNueva());

		return this.faltaPersistance.update(idFaltaActual, faltaActualizar);
	}

	private void assertDayIsTodayOrLater(LocalDate date) {
		if (date.isBefore(LocalDate.now())) {
			throw new ConflictException("La fecha es inferior al día de hoy");
		}
	}

	public void delete(IdFaltaDTO faltaDeleteInputDTO, UserInfo userInfoDTO) {

		/* Validar solo si es user */
		if (isUser(userInfoDTO)) {
			assertDayIsTodayOrLater(faltaDeleteInputDTO.getFecha());
			assertUserIspropietario(faltaDeleteInputDTO, userInfoDTO);
		}

		this.faltaPersistance.delete(faltaDeleteInputDTO);
	}

	private void assertUserIspropietario(IdFaltaDTO faltaDeleteInputDTO, UserInfo userInfoDTO) {
		Falta falta = faltaPersistance.readById(faltaDeleteInputDTO);
		if (!falta.getReferenciaProfesorPropietario().equals(userInfoDTO.referenciaProfesor())) {
			throw new ConflictException("No tienes permiso para realizar esta acción");
		}
	}

	public Falta cancelar(IdFaltaDTO faltaCancelarInput, UserInfo userInfo) {
		assertDayIsTodayOrLater(faltaCancelarInput.getFecha());

		Falta falta = faltaPersistance.readById(faltaCancelarInput);

		/* VALIDACIONES */
		if (!falta.getProfesorSustituto().isPresent()) {
			throw new ConflictException("No hay nadie que sustituya esta falta");
		}
		if (isUser(userInfo)) {
			if (falta.getReferenciaProfesorPropietario().equals(userInfo.referenciaProfesor())) {
				throw new ConflictException("No puedes cancelar el profesorSustituto de tu propia falta");
			}

			if (!falta.getReferenciaProfesorSustituto().equals(userInfo.referenciaProfesor())) {
				throw new ConflictException("No eres el profesor que sustituye esta falta");
			}
		}

		/* GUARDAR */
		falta.setProfesorSustituto(null);

		return this.faltaPersistance.update(faltaCancelarInput, falta);

	}

	private boolean isUser(UserInfo userInfo) {
		return userInfo.role().equals(RoleEnum.USER.toString());
	}

	public Falta sustituir(FaltaSustituirInputDTO faltaSustituirInput) {
		assertDayIsTodayOrLater(faltaSustituirInput.getFecha());

		IdFaltaDTO idFaltaDTO = new IdFaltaDTO(faltaSustituirInput.getReferenciaSesion(), faltaSustituirInput.getDia(),
				faltaSustituirInput.getIndice(), faltaSustituirInput.getFecha());

		Falta falta = faltaPersistance.readById(idFaltaDTO);

		/* VALIDACIONES */
		if (falta.getProfesorSustituto().isPresent()) {
			throw new ConflictException("Ya hay un profesor sustituyendo esta falta");
		}

		if (falta.getReferenciaProfesorPropietario().equals(faltaSustituirInput.getReferenciaProfesorSustituto())) {
			throw new ConflictException("No puedes sustituir tu propia falta");
		}

		/* GUARDAR */
		Profesor profesorSustituto = profesorPersistance
				.readByReferencia(faltaSustituirInput.getReferenciaProfesorSustituto());
		falta.setProfesorSustituto(profesorSustituto);

		return this.faltaPersistance.update(idFaltaDTO, falta);
	}

}
