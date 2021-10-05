package com.estudio.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootNominaApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(SpringBootNominaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootNominaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("inicio el proyecto");
		
		/*mongoTemplate.dropCollection("servicio").subscribe(); 
		mongoTemplate.dropCollection("calculo").subscribe();*/	
		
		/*String id = "614ac7000cdba60ce8b3ce42";
		service.findbyId(id).subscribe(s-> {log.info("prueba xd-> "+s);});*/
		
		/*String id = "614ac7000cdba60ce8b3ce41";
		service.findCalculoById(id).subscribe(s-> {log.info("prueba xd-> "+s);});*/
		
	}
}

