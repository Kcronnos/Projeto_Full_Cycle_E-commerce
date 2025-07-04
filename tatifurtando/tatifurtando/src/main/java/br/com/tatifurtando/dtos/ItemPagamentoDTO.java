package br.com.tatifurtando.dtos;

import java.math.BigDecimal;

public record ItemPagamentoDTO(
		long id,
		int quantidade,
	    BigDecimal precoitems,
	    JogoPagamentoDTO jogo
		) {

}
