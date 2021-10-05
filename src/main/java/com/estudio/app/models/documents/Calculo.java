package com.estudio.app.models.documents;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="calculo")
public class Calculo {
	
	@Id
	@NotNull
	private String id_calculo;

	private int normales = 0;

	private int nocturnas = 0;
	
	private int dominicales = 0;

	private int normales_extras = 0;
	
	private int nocturnas_extras = 0;

	private int dominicales_extras = 0;

	public Calculo() {}
	
	public Calculo(int normales, int nocturnas,
			int dominicales,  int normales_extras, int nocturnas_extras,
			int dominicales_extras) {
		this.normales = normales;
		this.nocturnas = nocturnas;
		this.dominicales = dominicales;
		this.normales_extras = normales_extras;
		this.nocturnas_extras = nocturnas_extras;
		this.dominicales_extras =dominicales_extras;
	}
	
	public String getId() {
		return id_calculo;
	}

	public void setId(String id) {
		this.id_calculo = id;
	}

	public int getNormales() {
		return normales;
	}

	public void setNormales(int normales) {
		this.normales = normales;
	}

	public int getNocturnas() {
		return nocturnas;
	}

	public void setNocturnas(int nocturnas) {
		this.nocturnas = nocturnas;
	}

	public int getDominicales() {
		return dominicales;
	}

	public void setDominicales(int dominicales) {
		this.dominicales = dominicales;
	}

	public int getNormales_extras() {
		return normales_extras;
	}

	public void setNormales_extras(int normales_extras) {
		this.normales_extras = normales_extras;
	}

	public int getNocturnas_extras() {
		return nocturnas_extras;
	}

	public void setNocturnas_extras(int nocturnas_extras) {
		this.nocturnas_extras = nocturnas_extras;
	}

	public int getDominicales_extras() {
		return dominicales_extras;
	}

	public void setDominicales_extras(int dominicales_extras) {
		this.dominicales_extras = dominicales_extras;
	}

	@Override
	public String toString() {
		return "Calculo [id_calculo=" + id_calculo + ", normales=" + normales + ", nocturnas=" + nocturnas + ", dominicales="
				+ dominicales + ", normales_extras=" + normales_extras + ", nocturnas_extras=" + nocturnas_extras
				+ ", dominicales_extras=" + dominicales_extras + "]";
	}

}
