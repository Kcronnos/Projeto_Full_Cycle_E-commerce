package br.com.tatifurtando.mappers;

import br.com.tatifurtando.dtos.UserCreateDTO;
import br.com.tatifurtando.dtos.UserResponseDTO;
import br.com.tatifurtando.entidades.User;

public class UserMapper {
	
	public static User toEntity(UserCreateDTO userCreateDTO) {
		
		User user = new User();
		user.setNome(userCreateDTO.nome());
		user.setEmail(userCreateDTO.email());
		user.setSenha(userCreateDTO.senha());
		
		return user;
	}
	
	public static UserResponseDTO toDTO(User user) {
		
		UserResponseDTO userResponse = new UserResponseDTO(user.getId(), user.getNome(), user.getEmail(), user.getSenha());
		
		return userResponse;
	}
}
