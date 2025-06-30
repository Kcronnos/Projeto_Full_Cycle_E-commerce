package br.com.tatifurtando.dtos;

public record UserResponseDTO(
		long id,
		String nome,
		String email,
		String senha
		) {}
