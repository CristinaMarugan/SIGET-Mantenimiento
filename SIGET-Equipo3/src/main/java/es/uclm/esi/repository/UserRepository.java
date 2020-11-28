package es.uclm.esi.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.uclm.esi.model.User;


public interface UserRepository extends MongoRepository<User, String> {
    
	 Optional<User> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	  
	  Optional<User> findById(int id);
	  
	  @Override
	  List<User> findAll(); 
    
}
