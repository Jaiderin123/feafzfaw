package com.estudio.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.estudio.app.models.documents.Servicio;

@Repository
public interface ServicioDao extends ReactiveMongoRepository<Servicio, String>{
}
