package es.uclm.esi.controller;

import java.lang.Enum.EnumDesc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.uclm.esi.model.Asistente;
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
@RequestMapping("")

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
	
	@PostMapping("/getRol")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> getRol(@RequestBody Map<String, Object> entrada,
			@RequestHeader("Authorization") String token){
		String rol;
		boolean admin =false;
		JSONObject user = new JSONObject(entrada);
		Optional<User> u= userRepository.findByUsername(user.getString("usuario"));
		Set<Role> roles =u.get().getRoles();
		for (Role s : roles) {
			rol =s.getName().name();
			if(rol.equals("ROLE_ADMIN"))
				admin=true;
		}
		if(admin)
			return ResponseEntity.ok(new MessageResponse("admin"));
		else
			return ResponseEntity.badRequest().body(new MessageResponse("user"));
	}
	
	@PostMapping("/deleteUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> eliminarUser(@RequestBody Map<String, Object> entrada,
			@RequestHeader("Authorization") String token){
		String rol;
		boolean admin =false;
		JSONObject user = new JSONObject(entrada);
		Optional<User> u= userRepository.findByUsername(user.getString("admin"));
		Set<Role> roles =u.get().getRoles();
		for (Role s : roles) {
			rol =s.getName().name();
			if(rol.equals("ROLE_ADMIN"))
				admin=true;
		}
		if(admin) {
			JSONArray usuarios = (JSONArray) user.get("asistentes");
			ArrayList<Asistente> usuariosE = new ArrayList<>();
			for (int i = 0; i < usuarios.length(); i++) {
				String nombre = (String) usuarios.get(i);
				Optional<User> usuario= userRepository.findByUsername(nombre);
				userRepository.deleteById(usuario.get().getId());
			}
			return ResponseEntity.ok(new MessageResponse("admin"));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("no eres un administrador"));
		}
	}
}
