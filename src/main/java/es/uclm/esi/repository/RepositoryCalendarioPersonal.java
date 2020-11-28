package es.uclm.esi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import es.uclm.esi.model.Reunion;

public interface RepositoryCalendarioPersonal extends MongoRepository<Reunion,String> {
	
	Reunion findById(int _id);

	@Query(value = "{'dia': ?0, 'mes': ?1, 'ano' : ?2 }")
	List<Reunion> findByDia(int dia, int mes, int ano);
	
	@Query(value = "{'mes': ?0, 'ano' : ?1 }")
	List<Reunion> findReunionesMes(int mes, int anio);

}
