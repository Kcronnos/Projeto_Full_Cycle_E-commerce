package br.com.tatifurtando.dtos;

public record UserCreateDTO(
		String nome,
		String email,
		String senha
		) {}
