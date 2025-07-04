package br.com.tatifurtando.dtos;

import java.math.BigDecimal;
import java.util.List;

import br.com.tatifurtando.entidades.ItemPedido;

public record PaymentRequestDTO(
		BigDecimal amount,
	    String token,
	    String paymentMethodId,
	    String payerEmail) {}
