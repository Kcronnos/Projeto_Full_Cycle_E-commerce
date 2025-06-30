package br.com.tatifurtando.dtos;

import java.math.BigDecimal;

import br.com.tatifurtando.entidades.User;
import br.com.tatifurtando.enuns.PedidoStatus;

public record PedidoCreateDTO(
		PedidoStatus pedidoStatus,
		BigDecimal total,
		User user
		) {}
