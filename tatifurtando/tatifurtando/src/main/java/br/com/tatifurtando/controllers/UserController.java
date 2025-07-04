package br.com.tatifurtando.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tatifurtando.dtos.UserCreateDTO;
import br.com.tatifurtando.dtos.UserLoginDTO;
import br.com.tatifurtando.dtos.UserLoginResponseDTO;
import br.com.tatifurtando.dtos.UserResponseDTO;
import br.com.tatifurtando.services.UserService;

@RestController
@RequestMapping("/tatifurtando/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> store(@RequestBody UserCreateDTO userCreateDTO) {
		return new ResponseEntity<>(userService.store(userCreateDTO), HttpStatus.CREATED);
	}

	@GetMapping("/showAll")
	public ResponseEntity<List<UserResponseDTO>> list() {
		return new ResponseEntity<>(userService.list(), HttpStatus.OK);
	}

	@GetMapping("/show/{id_user}")
	public ResponseEntity<UserResponseDTO> show(@PathVariable long id_user) {
		try {
			return new ResponseEntity<>(userService.show(id_user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userloginDTO) {
        try {
            UserLoginResponseDTO response = userService.login(userloginDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

	@DeleteMapping("/delete/{id_user}")
	public ResponseEntity<String> destroy(@PathVariable long id_user) {
		try {
			userService.destroy(id_user);
			return new ResponseEntity("Usuário deletado com sucesso.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/users/check-email")
	public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
		boolean exists = userService.emailExists(email);
	    return ResponseEntity.ok(exists);
	}

}
