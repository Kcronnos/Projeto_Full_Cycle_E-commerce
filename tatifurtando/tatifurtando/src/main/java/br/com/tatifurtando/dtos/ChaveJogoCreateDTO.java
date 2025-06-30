package br.com.tatifurtando.dtos;

import br.com.tatifurtando.entidades.Jogo;
import br.com.tatifurtando.enuns.ChaveStatus;

public record ChaveJogoCreateDTO(
		String chave,
		ChaveStatus chaveStatus,
		Jogo jogo
		) {}
