package br.com.tatifurtando.dtos;

import java.math.BigDecimal;

import br.com.tatifurtando.entidades.ItemPedido;

public record JogoResponseDTO(
		long id,
		String nome,
		String descricao,
		BigDecimal preco,
		String desenvolvedora,
		String imagemUrl
		) {}
