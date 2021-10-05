package com.estudio.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.app.models.dao.CalculoDao;
import com.estudio.app.models.dao.ServicioDao;
import com.estudio.app.models.documents.Calculo;
import com.estudio.app.models.documents.Servicio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServicioServiceImpl implements ServicioService {
	
	@Autowired
	private ServicioDao dao;
	
	@Autowired
	private CalculoDao daoCalculo;

	@Override
	public Flux<Servicio> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Servicio> findbyId(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<Servicio> save(Servicio servicio) {
		return dao.save(servicio);
	}
	
	/*@Override
	public Mono<Servicio> edit(Servicio servicio) {
		return dao.save(servicio);
	}*/

	@Override
	public Mono<Void> delete(Servicio servicio) {
		return dao.delete(servicio);
	}

	//CALCULO
	@Override
	public Mono<Calculo> saveCalculo(Calculo calculo) {
		return daoCalculo.save(calculo);
	}

	@Override
	public Flux<Calculo> findAllCalculo() {
		return daoCalculo.findAll();
	}

	@Override
	public Mono<Calculo> findCalculoById(String id) {
		return daoCalculo.findById(id);
	}
}
