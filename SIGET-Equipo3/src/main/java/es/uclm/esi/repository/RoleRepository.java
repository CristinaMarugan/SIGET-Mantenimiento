package es.uclm.esi.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.uclm.esi.model.ERole;
import es.uclm.esi.model.Role;


public interface RoleRepository extends MongoRepository<Role, String> {
    
	 Optional<Role> findByName(ERole name);
    
}
