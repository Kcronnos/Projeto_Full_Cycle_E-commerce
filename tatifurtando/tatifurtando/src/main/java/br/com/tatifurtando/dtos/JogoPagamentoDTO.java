package br.com.tatifurtando.dtos;

import java.math.BigDecimal;

public record JogoPagamentoDTO(
		Long id,
	    String nome,
	    BigDecimal preco
		) {

}
