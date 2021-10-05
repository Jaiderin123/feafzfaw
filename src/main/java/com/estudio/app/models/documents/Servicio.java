package com.estudio.app.models.documents;

import javax.validation.constraints.NotNull;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="servicio")
public class Servicio {
	
	@Id
	@NotNull
	private String id;

	@NotNull
	private String id_tecnico;
	
	@NotNull
	private String nombre_servicio;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private DateTime fecha_inicio;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private DateTime fecha_final;
	
	@NotNull
	private Calculo calculo;
	
	public Servicio() {
	}

	//CONST CON CALCULO y SEMANA_Año
	public Servicio(@NotNull String id_tecnico, @NotNull String nombre_servicio, @NotNull DateTime fecha_inicio,
			@NotNull DateTime fecha_final, Calculo calculo) {
		this(id_tecnico,nombre_servicio,fecha_inicio,fecha_final);
		this.calculo = calculo;
	}
	
	//CONST SIN CALCULO
	public Servicio(@NotNull String id_tecnico, @NotNull String nombre_servicio, @NotNull DateTime fecha_inicio,
			@NotNull DateTime fecha_final) {
		this.id_tecnico = id_tecnico;
		this.nombre_servicio = nombre_servicio;
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_tecnico() {
		return id_tecnico;
	}

	public void setId_tecnico(String id_tecnico) {
		this.id_tecnico = id_tecnico;
	}

	public String getNombre_servicio() {
		return nombre_servicio;
	}

	public void setNombre_servicio(String nombre_servicio) {
		this.nombre_servicio = nombre_servicio;
	}

	public DateTime getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(DateTime fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public DateTime getFecha_final() {
		return fecha_final;
	}

	public void setFecha_final(DateTime fecha_final) {
		this.fecha_final = fecha_final;
	}

	@Override
	public String toString() {
		return "Servicio [id_tecnico=" + id_tecnico + ", nombre_servicio=" + nombre_servicio
				+ ", fecha_inicio=" + fecha_inicio + ", fecha_final=" + fecha_final + "]";
	}
	
	//get y set con calcuo
	public Calculo getCalculo() {
		return calculo;
	}

	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}

	
}
