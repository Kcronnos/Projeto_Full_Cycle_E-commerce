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

import br.com.tatifurtando.dtos.PedidoCreateDTO;
import br.com.tatifurtando.dtos.PedidoResponseDTO;
import br.com.tatifurtando.services.PedidoService;

@RestController
@RequestMapping("/tatifurtando/pedidos")
public class PedidoController {

	@Autowired
	PedidoService pedidoService;
	
	@PostMapping("/register")
	public ResponseEntity<PedidoResponseDTO> store(@RequestBody PedidoCreateDTO pedidoCreateDTO) {
		return new ResponseEntity<>(pedidoService.store(pedidoCreateDTO), HttpStatus.CREATED);
	}

	@GetMapping("/showAll")
	public ResponseEntity<List<PedidoResponseDTO>> list() {
		return new ResponseEntity<>(pedidoService.list(), HttpStatus.OK);
	}

	@GetMapping("/show/{id_pedido}")
	public ResponseEntity<PedidoResponseDTO> show(@PathVariable long id_pedido) {
		try {
			return new ResponseEntity<>(pedidoService.show(id_pedido), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id_pedido}")
	public ResponseEntity<String> destroy(@PathVariable long id_pedido) {
		try {
			pedidoService.destroy(id_pedido);
			return new ResponseEntity("Pedido deletado com sucesso.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
