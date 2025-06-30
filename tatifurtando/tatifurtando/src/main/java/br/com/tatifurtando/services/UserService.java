package br.com.tatifurtando.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tatifurtando.dtos.UserCreateDTO;
import br.com.tatifurtando.dtos.UserResponseDTO;
import br.com.tatifurtando.entidades.User;
import br.com.tatifurtando.mappers.UserMapper;
import br.com.tatifurtando.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
		
	public UserResponseDTO store(UserCreateDTO userCreateDTO) {
		
		User user = UserMapper.toEntity(userCreateDTO);
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
		User userResponse = userRepository.save(user);
		
		return UserMapper.toDTO(userResponse);
	}
	
	public List<UserResponseDTO> list(){
				
		return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
	}
	
	public UserResponseDTO show(long id) {
		
		User user = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuário com o id "+id+" não foi encontrado.")
		);
			
		return UserMapper.toDTO(user);
	}
	
	/*public UserResponseDTO update(UserUpdateDTO userUpdateDTO) {
		
		User user = userRepository
				.findById(userUpdateDTO.id())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado para alteração"));
		
		user.setName(userUpdateDTO.name());
		user.setEmail(userUpdateDTO.email());
		user.setRole(userUpdateDTO.role());
		user.setPassword(userUpdateDTO.password());
				
		
		return UserMapper.toDTO(userRepository.save(user));
	}*/
	
	public void destroy(long id) {
		
		User user = userRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado para deleção"));
		
		userRepository.delete(user);
		
	}
}
