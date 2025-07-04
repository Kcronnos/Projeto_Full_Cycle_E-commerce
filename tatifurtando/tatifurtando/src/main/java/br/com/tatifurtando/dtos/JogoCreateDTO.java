package br.com.tatifurtando.dtos;

import java.math.BigDecimal;

import br.com.tatifurtando.entidades.ItemPedido;

public record JogoCreateDTO(
		String nome,
		String descricao,
		BigDecimal preco,
		String desenvolvedora,
		String imagemurl
		) {}
