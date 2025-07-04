package br.com.tatifurtando.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.tatifurtando.dtos.JogoCreateDTO;
import br.com.tatifurtando.dtos.JogoResponseDTO;
import br.com.tatifurtando.services.JogoService;

@RestController
@RequestMapping("/tatifurtando/jogos")
public class JogoController {

	@Autowired
	JogoService jogoService;
	
	@PostMapping("/register")
	public ResponseEntity<JogoResponseDTO> store(@RequestBody JogoCreateDTO jogoCreateDTO) {
		return new ResponseEntity<>(jogoService.store(jogoCreateDTO), HttpStatus.CREATED);
	}

	@GetMapping("/showAll")
	public ResponseEntity<List<JogoResponseDTO>> list() {
		return new ResponseEntity<>(jogoService.list(), HttpStatus.OK);
	}

	@GetMapping("/show/{id_jogo}")
	public ResponseEntity<JogoResponseDTO> show(@PathVariable long id_jogo) {
		try {
			return new ResponseEntity<>(jogoService.show(id_jogo), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id_jogo}")
	public ResponseEntity<String> destroy(@PathVariable long id_jogo) {
		try {
			jogoService.destroy(id_jogo);
			return new ResponseEntity("Jogo deletado com sucesso.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/importar-populares")
    public ResponseEntity<String> importarJogosPopulares() {
        try {
            jogoService.importarJogosPopularesComDetalhes();
            return ResponseEntity.ok("Importação de jogos populares concluidas com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro ao importar jogos populares: " + e.getMessage());
        }
    }

}
