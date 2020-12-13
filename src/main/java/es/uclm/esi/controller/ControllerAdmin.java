package es.uclm.esi.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.uclm.esi.model.ERole;
import es.uclm.esi.model.Role;
import es.uclm.esi.model.User;
import es.uclm.esi.payload.request.SignupRequest;
import es.uclm.esi.payload.response.MessageResponse;
import es.uclm.esi.repository.RepositoryReuniones;
import es.uclm.esi.repository.RoleRepository;
import es.uclm.esi.repository.UserRepository;
import es.uclm.esi.security.jwt.JwtUtils;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")

public class ControllerAdmin {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	
	@Value("${siget.app.jwtSecret}")
	private String jwtSecret;
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		        encoder.encode(signUpRequest.getPassword()), signUpRequest.getName(), signUpRequest.getApellidos(),
		        signUpRequest.getTlf(), signUpRequest.getDni());
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	
	public ResponseEntity<?> eliminarUsuario(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		        encoder.encode(signUpRequest.getPassword()), signUpRequest.getName(), signUpRequest.getApellidos(),
		        signUpRequest.getTlf(), signUpRequest.getDni());
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
}
