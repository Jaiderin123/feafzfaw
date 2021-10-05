package com.estudio.app.models.services;

import com.estudio.app.models.documents.Calculo;
import com.estudio.app.models.documents.Servicio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioService {

	public Flux<Servicio> findAll();
	
	public Mono<Servicio> findbyId(String id);
	
	public Mono<Servicio> save(Servicio servicio);
	
	public Mono<Void> delete(Servicio servicio);
	
	
	//CALCULOS
	public Mono<Calculo> saveCalculo(Calculo calculo);
	
	public Flux<Calculo> findAllCalculo();
	
	public Mono<Calculo> findCalculoById(String id);
	
}
