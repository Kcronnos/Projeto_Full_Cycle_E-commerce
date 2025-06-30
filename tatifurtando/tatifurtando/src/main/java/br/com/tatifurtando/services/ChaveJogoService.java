package br.com.tatifurtando.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tatifurtando.dtos.ChaveJogoCreateDTO;
import br.com.tatifurtando.dtos.ChaveJogoResponseDTO;
import br.com.tatifurtando.entidades.ChaveJogo;
import br.com.tatifurtando.mappers.ChaveJogoMapper;
import br.com.tatifurtando.repositories.ChaveJogoRepository;

@Service
public class ChaveJogoService {

	@Autowired
	ChaveJogoRepository chaveJogoRepository;
	
	
	public ChaveJogoResponseDTO store(ChaveJogoCreateDTO chaveJogoCreateDTO) {
		
		ChaveJogo chaveJogo = ChaveJogoMapper.toEntity(chaveJogoCreateDTO);
				
		return ChaveJogoMapper.toDTO(chaveJogoRepository.save(chaveJogo));
	}
	
	public List<ChaveJogoResponseDTO> list(){
		return chaveJogoRepository.findAll().stream().map(ChaveJogoMapper::toDTO).toList();
	}
		
	public ChaveJogoResponseDTO show(long id) {
		ChaveJogo chaveJogo = chaveJogoRepository.findById(id).orElseThrow(() -> new RuntimeException("Chave do Jogo não encontrado"));
		
		return ChaveJogoMapper.toDTO(chaveJogo);
	}
	
	public void destroy(long id) {
		ChaveJogo chaveJogo = chaveJogoRepository.findById(id).orElseThrow(() -> new RuntimeException("Chave do Jogo não encontrado"));
		
		chaveJogoRepository.delete(chaveJogo);
	}
}
