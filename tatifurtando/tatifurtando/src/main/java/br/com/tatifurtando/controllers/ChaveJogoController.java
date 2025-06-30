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
import org.springframework.web.bind.annotation.RestController;

import br.com.tatifurtando.dtos.ChaveJogoCreateDTO;
import br.com.tatifurtando.dtos.ChaveJogoResponseDTO;
import br.com.tatifurtando.services.ChaveJogoService;

@RestController
@RequestMapping("/tatifurtando/chavejogo")
public class ChaveJogoController {

	@Autowired
	ChaveJogoService chaveJogoService;
	
	@PostMapping("/register")
	public ResponseEntity<ChaveJogoResponseDTO> store(@RequestBody ChaveJogoCreateDTO chaveJogoCreateDTO) {
		return new ResponseEntity<>(chaveJogoService.store(chaveJogoCreateDTO), HttpStatus.CREATED);
	}

	@GetMapping("/showAll")
	public ResponseEntity<List<ChaveJogoResponseDTO>> list() {
		return new ResponseEntity<>(chaveJogoService.list(), HttpStatus.OK);
	}

	@GetMapping("/show/{id_chavejogo}")
	public ResponseEntity<ChaveJogoResponseDTO> show(@PathVariable long id_chavejogo) {
		try {
			return new ResponseEntity<>(chaveJogoService.show(id_chavejogo), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id_chavejogo}")
	public ResponseEntity<String> destroy(@PathVariable long id_chavejogo) {
		try {
			chaveJogoService.destroy(id_chavejogo);
			return new ResponseEntity("Chave do Jogo deletado com sucesso.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
