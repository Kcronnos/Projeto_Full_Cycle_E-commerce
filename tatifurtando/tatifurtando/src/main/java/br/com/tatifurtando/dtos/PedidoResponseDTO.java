package br.com.tatifurtando.dtos;

import java.math.BigDecimal;
import java.sql.Date;

import br.com.tatifurtando.entidades.User;
import br.com.tatifurtando.enuns.PedidoStatus;

public record PedidoResponseDTO(
		long id,
		PedidoStatus pedidoStatus,
		BigDecimal total,
		Date dataCriado,
		User user
		){}
