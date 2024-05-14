package com.faltasproject.utils;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.faltasproject.adapters.jpa.clases.daos.AulaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.FaltaRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.GuardiaRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.HoraHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.security.usuarios.daos.RoleRepositoryJPA;
import com.faltasproject.security.usuarios.daos.UserRepositoryJPA;
import com.faltasproject.security.usuarios.entity.RoleEntity;
import com.faltasproject.security.usuarios.entity.UsuarioEntity;

import jakarta.transaction.Transactional;

@Component
public class InitialDataBase {
	private final GuardiaRepositoryJPA guardiaRepositoryJPA;
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	private final SesionRepositoryJPA sesionRepositoryJPA;
	private final HoraHorarioRepositoryJPA horaHorarioRepositoryJPA;
	private final FaltaRepositoryJPA faltaRepositoryJPA;

	private ProfesorRepositoryJPA profesorRepositoryJPA;

	private final MateriaRepositoryJPA materiaRepository;
	private final CursoRepositoryJPA cursoRepositoryJPA;
	private final AulaRepositoryJPA aulaRepositoryJPA;
	private final GrupoRepositoryJPA grupoRepositoryJPA;

	private final UserRepositoryJPA userRepositoryJPA;
	private final RoleRepositoryJPA roleRepositoryJPA;

	public InitialDataBase(GuardiaRepositoryJPA guardiaRepositoryJPA,TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA, SesionRepositoryJPA sesionRepositoryJPA,
			HoraHorarioRepositoryJPA horaHorarioRepositoryJPA, FaltaRepositoryJPA faltaRepositoryJPA,

			ProfesorRepositoryJPA profesorRepositoryJPA,

			MateriaRepositoryJPA materiaRepository, CursoRepositoryJPA cursoRepositoryJPA,
			AulaRepositoryJPA aulaRepositoryJPA, GrupoRepositoryJPA grupoRepositoryJPA,

			UserRepositoryJPA userRepositoryJPA, RoleRepositoryJPA roleRepositoryJPA

	) {
		this.guardiaRepositoryJPA = guardiaRepositoryJPA;
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
		this.sesionRepositoryJPA = sesionRepositoryJPA;
		this.horaHorarioRepositoryJPA = horaHorarioRepositoryJPA;
		this.faltaRepositoryJPA = faltaRepositoryJPA;

		this.profesorRepositoryJPA = profesorRepositoryJPA;

		this.materiaRepository = materiaRepository;
		this.cursoRepositoryJPA = cursoRepositoryJPA;
		this.aulaRepositoryJPA = aulaRepositoryJPA;
		this.grupoRepositoryJPA = grupoRepositoryJPA;
		this.profesorRepositoryJPA = profesorRepositoryJPA;

		this.userRepositoryJPA = userRepositoryJPA;
		this.roleRepositoryJPA = roleRepositoryJPA;
	}
	
	public void ClearToInitialDatabase() {

		Optional<RoleEntity> roleEnum = roleRepositoryJPA.findByRoleEnum(RoleEnum.USER);
		if(!roleEnum.isPresent()) {
			populateInitialDataIfNotExist();
		}

		guardiaRepositoryJPA.deleteAll();
		/* HORARIO */
		faltaRepositoryJPA.deleteAll();
		horaHorarioRepositoryJPA.deleteAll();
		tramoHorarioRepositoryJPA.deleteAll();
		/* PROFESORADO */
		this.profesorRepositoryJPA.deleteAll();
		/* CLASES */
		materiaRepository.deleteAll();
		grupoRepositoryJPA.deleteAll();
		cursoRepositoryJPA.deleteAll();
		aulaRepositoryJPA.deleteAll();
		sesionRepositoryJPA.deleteAll();

		userRepositoryJPA.deleteAllByRole(roleEnum.get());
	}
	
	public void populateInitialDataIfNotExist() {
		/* ROLES */
		Optional<RoleEntity> roleAdmin = roleRepositoryJPA.findByRoleEnum(RoleEnum.ADMIN);
		if (!roleAdmin.isPresent()) {
			roleAdmin = Optional.of(roleRepositoryJPA.save(new RoleEntity(RoleEnum.ADMIN)));
		}

		Optional<RoleEntity> roleUser = roleRepositoryJPA.findByRoleEnum(RoleEnum.USER);
		if (!roleUser.isPresent()) {
			roleUser = Optional.of(roleRepositoryJPA.save(new RoleEntity(RoleEnum.USER)));
		}

		/* USUARIO */
		Optional<UsuarioEntity> admin = userRepositoryJPA.findByUsername("admin");
		if (!admin.isPresent()) {
			UsuarioEntity adminUser = new UsuarioEntity().builder().username("admin")
					.password("$2a$10$qQWsDs2i7N1qDx61DK6W1Ou8hEmUcl5QtQ53tE4Hiw2o2lsRwj1Fi") // admin
					.role(roleAdmin.get()).isEnabled(true) // cuenta activa
					.accountNoExpired(true) // cuenta no expirada
					.accountNoLocked(true) // cuenta no bloqueada
					.credentialNoExpired(true) // credenciales no expiradas
					.build();

			userRepositoryJPA.save(adminUser);

		}

	}
}
