package com.estudio.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.estudio.app.models.documents.Calculo;

public interface CalculoDao extends ReactiveMongoRepository<Calculo, String>{

}
