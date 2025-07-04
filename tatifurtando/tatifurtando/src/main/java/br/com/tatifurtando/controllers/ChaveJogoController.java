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

	@GetMapping("/porItemPedido/{id_item_pedido}")
	public ResponseEntity<List<ChaveJogoResponseDTO>> listarChavesPorItemPedido(@PathVariable long id_item_pedido) {
	    try {
	        List<ChaveJogoResponseDTO> chaves = chaveJogoService.listarPorItemPedido(id_item_pedido);
	        return new ResponseEntity<>(chaves, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
