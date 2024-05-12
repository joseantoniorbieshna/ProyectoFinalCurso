package com.faltasproject.adapters.jpa.profesorado.entities;

import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.models.usuario.Usuario;
import com.faltasproject.security.usuarios.entity.UsuarioEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROFESORES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProfesorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@EqualsAndHashCode.Include
	@Column(unique = true)
	private String referencia;
	private String nombre;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "profesor",cascade = CascadeType.REMOVE)
	private Set<SesionEntity> materia;
	
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "usuario_id", unique = true)
    private UsuarioEntity usuario;
	
	public ProfesorEntity(Profesor profesor) {
		fromProfesor(profesor);
	}
	
	public ProfesorEntity(String referencia) {
		super();
		this.referencia = referencia;
	}
	
	public ProfesorEntity(String referencia, String nombre) {
		this(referencia);
		this.nombre = nombre;
	}
	
	public void fromProfesor(Profesor profesor) {
		if(profesor.getReferencia()!=null) { setReferencia(profesor.getReferencia()); }
		if(profesor.getNombre()!=null) { setNombre(profesor.getNombre()); }
		if(profesor.getUsuario()!=null) {setUsuario(usuario);}
	}
	
	public Profesor toProfesor() {
		if(usuario!=null) {
			return new Profesor(this.referencia,this.nombre,usuario.toUsuario());
		}
		return new Profesor(this.referencia,this.nombre);
	}

	
	
}
