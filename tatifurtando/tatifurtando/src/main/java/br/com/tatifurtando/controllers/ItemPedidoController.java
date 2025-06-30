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

import br.com.tatifurtando.dtos.ItemPedidoCreateDTO;
import br.com.tatifurtando.dtos.ItemPedidoResponseDTO;
import br.com.tatifurtando.services.ItemPedidoService;

@RestController
@RequestMapping("/tatifurtando/Itenspedidos")
public class ItemPedidoController {

	@Autowired
	ItemPedidoService itemPedidoService;
	
	@PostMapping("/register")
	public ResponseEntity<ItemPedidoResponseDTO> store(@RequestBody ItemPedidoCreateDTO itemPedidoCreateDTO) {
		return new ResponseEntity<>(itemPedidoService.store(itemPedidoCreateDTO), HttpStatus.CREATED);
	}

	@GetMapping("/showAll")
	public ResponseEntity<List<ItemPedidoResponseDTO>> list() {
		return new ResponseEntity<>(itemPedidoService.list(), HttpStatus.OK);
	}

	@GetMapping("/show/{id_itempedido}")
	public ResponseEntity<ItemPedidoResponseDTO> show(@PathVariable long id_itempedido) {
		try {
			return new ResponseEntity<>(itemPedidoService.show(id_itempedido), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id_itempedido}")
	public ResponseEntity<String> destroy(@PathVariable long id_itempedido) {
		try {
			itemPedidoService.destroy(id_itempedido);
			return new ResponseEntity("Item Pedido deletado com sucesso.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
