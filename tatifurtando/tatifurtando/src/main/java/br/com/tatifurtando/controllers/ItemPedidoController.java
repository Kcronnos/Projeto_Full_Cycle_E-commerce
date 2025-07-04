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

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import br.com.tatifurtando.dtos.ItemPedidoCreateDTO;
import br.com.tatifurtando.dtos.ItemPedidoResponseDTO;
import br.com.tatifurtando.dtos.PaymentRequestDTO;
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
	
	@GetMapping("/byPedido/{id_pedido}")
	public ResponseEntity<List<ItemPedidoResponseDTO>> listarItensPorPedido(@PathVariable long id_pedido) {
	    List<ItemPedidoResponseDTO> itens = itemPedidoService.listarPorPedido(id_pedido);
	    return new ResponseEntity<>(itens, HttpStatus.OK);
	}
	
	@PostMapping("/process_payment")
	public ResponseEntity<?> criarPagamento(@RequestBody PaymentRequestDTO paymentRequest) {
	    try {
	        MercadoPagoConfig.setAccessToken("TEST-656783375639768-070316-9a319eced36caccd18af7615a773ff8a-556937756");

	        PaymentClient paymentClient = new PaymentClient();

	        PaymentCreateRequest request = PaymentCreateRequest.builder()
	            .transactionAmount(paymentRequest.amount())
	            .token(paymentRequest.token())
	            .description("Compra de jogos")
	            .installments(1)
	            .paymentMethodId(paymentRequest.paymentMethodId())
	            .payer(PaymentPayerRequest.builder()
	                .email(paymentRequest.payerEmail())
	                .build())
	            .build();

	        Payment payment = paymentClient.create(request);
	        return ResponseEntity.ok(payment);

	    } catch (MPApiException | MPException e) {
	        System.err.println("Erro da API do Mercado Pago:");
	        System.err.println("Status: " + ((MPApiException) e).getApiResponse().getStatusCode());
	        System.err.println("Content: " + ((MPApiException) e).getApiResponse().getContent());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Erro ao processar pagamento: " + e.getMessage());
	    }
	}
}
