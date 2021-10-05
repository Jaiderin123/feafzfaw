package com.estudio.app.controllers;

import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudio.app.SpringBootNominaApplication;
import com.estudio.app.models.documents.Calculo;
import com.estudio.app.models.documents.Servicio;
import com.estudio.app.models.services.ServicioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/servicios"})
public class ServicioController {
 
	@Autowired
	private ServicioService service;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootNominaApplication.class);
	
	//VARIABLES
	boolean horario_laboral = false;
	
	//FECHAs
	DateTime f_inicio;
	DateTime f_final;
	//HORA
	int day;
	int hora_inicio;
	int hora_final;
	//DIFERENCIA HORA
	int period;
	int restantes = 0;
	//SI EXISTE EL TECNICO
	boolean existe = false;
	//intervalo de tiempo por semana
	int intervalo;

	//SERVICIOS
	@GetMapping()
	public Flux<Servicio> listar(){
		return service.findAll();
	}
	
	//CALCULOS
	@GetMapping("/calculos")
	public Flux<Calculo> listarCalcuo(){
		return service.findAllCalculo();
	}
	
	//VER SERVICIO
	@GetMapping(path = {"/{id}"})
	public Mono<Servicio> ver(@PathVariable("id")String id){
		log.info("Entro al de servicio??");
		return service.findbyId(id);
	}
	
	//VER CALCULO
	@GetMapping(path = {"/calculo/{id}"})
	public Mono<Object> verCalculo(@PathVariable("id")String id){
		return service.findbyId(id).map(s -> {
			String id_calculo = s.getCalculo().getId();
			//log.info("entro al map");
			return service.findCalculoById(id_calculo);
		});
	}
	
	//BUSCAR normal
	@GetMapping(path = {"/buscar/{cc}/{week}"})
	public Flux<Servicio> buscar(@PathVariable("cc")String cc,@PathVariable("week")String week ){
		Flux<Servicio> Servicios = service.findAll();
		//log.info("C.C->"+cc+" WEEK->"+week);
		int xd = Integer.parseInt(week);
		
		//inicializar en 0 para no sumar cada vez que se consulta
		intervalo = 0;

		return Servicios.filter(s-> s.getId_tecnico().equals(cc) && s.getFecha_inicio().getWeekOfWeekyear() == xd)
		.doOnNext(busqueda -> {
			log.info("entro a la busqueda");
			log.info("resultdo->\n"+busqueda+"\n");
			
			intervalo = intervalo + Hours.hoursBetween(busqueda.getFecha_inicio(), busqueda.getFecha_final()).getHours();
		}).doFinally(ad -> {
			log.info("entro al finally");
			log.info("periodo de horas->"+intervalo+"\n");
			
			return;
		});
			
			//.filter(s-> s.getFecha_inicio().getWeekOfWeekyear() == xd)
			//.filter(s-> s.getId_tecnico().equals(cc))
	}
	
	//OBTENER INTERVALO DE HORAS SEMANALAS
	@GetMapping(path = {"/intervalo"})
	public int intervalo() {
		log.info("en la otra funcion->"+intervalo);
		return intervalo;
	}

	//CREAR SERVICIO
	@PostMapping()
	public Mono<Servicio> crear(@RequestBody Servicio servicio){
		f_final = servicio.getFecha_final();
		f_inicio = servicio.getFecha_inicio();
		day = f_inicio.getDayOfWeek();
		hora_inicio = f_inicio.getHourOfDay();
		hora_final = f_final.getHourOfDay();
		period = Hours.hoursBetween(f_inicio, f_final).getHours();
		
		Collection<Servicio> Servicios = service.findAll().collectList().block();

		//TECNICO YA EXISTENTE
		for(Servicio s : Servicios){
			if(servicio.getId_tecnico().equals(s.getId_tecnico())) {
				existe=true;
				log.info("entro a otro");
				
				//VALIDAR HORARIO LABORAL
				if(hora_inicio>=7 && hora_final<=20) {
					horario_laboral = true;
					log.info("horario->"+horario_laboral);
				}
				
				//NORMALES y EXtRAS
				if(day >=1 && day <= 6 && horario_laboral == true) {
					log.info("Se entro OTRO NORMALES");
					
					service.findCalculoById(s.getCalculo().getId()).flatMap(c -> {
						
						int normales = c.getNormales();
						int normales_extras = c.getNormales_extras();
						
						//EXTRA O NO
						if(normales >=48) {
							log.info("ENTRO EN EXTRAS JEJE");
							c.setNormales_extras(normales_extras+period);
							servicio.setCalculo(c);
							service.saveCalculo(c).subscribe();
						}else{
							log.info("ENTRO EN normales :cc.i.");
							c.setNormales(normales+period);
							servicio.setCalculo(c);
							service.saveCalculo(c).subscribe();
						}

						return service.save(servicio);
					}).subscribe(x ->{log.info("termino proceso normales\n"+x);});
				}
				
				//NOCTURNAS Y extras
				else if(day >=1 && day <= 6 &&  horario_laboral == false) { 
					log.info("Se entro OTRO NOCTURNAS");
					
					service.findCalculoById(s.getCalculo().getId()).flatMap(c -> {
						
						int nocturnas = c.getNocturnas();
						
						int nocturnas_extras = c.getNocturnas_extras();
						
						//EXTRA O NO
						if(nocturnas >=48) {
							log.info("ENTRO EN EXTRAS");
							c.setNocturnas_extras(nocturnas_extras+period);
							servicio.setCalculo(c);
							service.saveCalculo(c).subscribe();
						}else{
							log.info("ENTRO EN nocturnas");
							c.setNocturnas(nocturnas+period);
							servicio.setCalculo(c);
							service.saveCalculo(c).subscribe();
						}
						
						return service.save(servicio);
					}).subscribe(x ->{log.info("termino proceso nocturnas\n"+x);});
				}
				
				//DOMINICALES y extras
				else if(day==7) {
					log.info("Se entro OTRO DOMINICALES");
					
					service.findCalculoById(s.getCalculo().getId()).flatMap(c -> {
						int dominicales = c.getDominicales();
						
						int dominicales_extras = c.getDominicales_extras();
						
						//EXTRA O NO
						if(dominicales >=48) {
							log.info("ENTRO EN EXTRAS");
							c.setDominicales_extras(dominicales_extras+period);
							servicio.setCalculo(c);
							service.saveCalculo(c).subscribe();
						}else{
							log.info("ENTRO EN dominicales");
							c.setDominicales(dominicales+period);
							servicio.setCalculo(c);
							service.saveCalculo(c).subscribe();
						}
						
						return service.save(servicio);
					}).subscribe(x ->{log.info("termino proceso dominicales\n"+x);});
				}
				break;
			}
		}//FIN CICLO TECNICO NUEVO
		
		//NUEVO TECNICO
		if(existe==false) {
			//VALIDAR HORARIO LABORAL
			if(hora_inicio>=7 && hora_final<=20) {
				horario_laboral = true;
				log.info("horario->"+horario_laboral);
			}
			
			//NORMALES
			if(day >=1 && day <= 6 && horario_laboral == true) {
				log.info("Se entro NEW NORMALES");
				
				Calculo calculo = new Calculo();
				service.saveCalculo(calculo).flatMap(c -> {
					c.setNormales(period);
					
					return service.saveCalculo(calculo);
				}).doOnNext(z->{
					servicio.setCalculo(z);
					service.save(servicio).subscribe();
				})
				.subscribe(x ->{log.info("ID DEL CALCULO NUEVO-> "+ x.getId());});
				
				return service.save(servicio);
			}
			
			//NOCTURNAS
			else  if(day >=1 && day <= 6 &&  horario_laboral == false) { 
				log.info("Se entro NEW NOCTURNAS");
				
				Calculo calculo = new Calculo();
				service.saveCalculo(calculo).flatMap(c -> {
					c.setNocturnas(period);
					
					return service.saveCalculo(calculo);
				}).doOnNext(z->{
					servicio.setCalculo(z);
					service.save(servicio).subscribe();
				})
				.subscribe(x ->{log.info("ID DEL CALCULO NUEVO-> "+ x.getId());});
				
				return service.save(servicio);
			}
			
			//DOMINICALES
			else if(day==7) {
				log.info("Se entro NEW DOMINICALES");
			
				Calculo calculo = new Calculo();
				service.saveCalculo(calculo).flatMap(c -> {
					c.setDominicales(period);
					
					return service.saveCalculo(calculo);
				}).doOnNext(z->{
					servicio.setCalculo(z);
					service.save(servicio).subscribe();
				})
				.subscribe(x ->{log.info("ID DEL CALCULO NUEVO-> "+ x.getId());});
				
				return service.save(servicio);
			}
		}//FIN TECNICO NUEVO
		
		return null;
	}
	
	/*
	public Mono<Calculo> editar(Servicio servicio, Calculo calculo){
		log.info("ENTRO AL EDITAR");
		return service.saveCalculo(calculo);
	}*/
}
