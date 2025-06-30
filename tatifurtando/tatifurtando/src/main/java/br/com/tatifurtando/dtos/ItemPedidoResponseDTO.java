package br.com.tatifurtando.dtos;

import java.math.BigDecimal;

import br.com.tatifurtando.entidades.Jogo;
import br.com.tatifurtando.entidades.Pedido;

public record ItemPedidoResponseDTO(
		long id,
		int quantidade,
		BigDecimal precoItems,
		Pedido pedido,
		Jogo jogo
		) {}
