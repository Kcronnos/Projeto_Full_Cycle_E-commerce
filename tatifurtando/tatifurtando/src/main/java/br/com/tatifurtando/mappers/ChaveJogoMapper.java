package br.com.tatifurtando.mappers;

import br.com.tatifurtando.dtos.ChaveJogoCreateDTO;
import br.com.tatifurtando.dtos.ChaveJogoResponseDTO;
import br.com.tatifurtando.entidades.ChaveJogo;

public class ChaveJogoMapper {

	public static ChaveJogo toEntity(ChaveJogoCreateDTO chaveJogoCreateDTO) {
		
		ChaveJogo chaveJogo = new ChaveJogo();
		chaveJogo.setChave(chaveJogoCreateDTO.chave());
		chaveJogo.setChaveStatus(chaveJogoCreateDTO.chaveStatus());
		chaveJogo.setJogo(chaveJogoCreateDTO.jogo());
		
		return chaveJogo;
	}
	
	public static ChaveJogoResponseDTO toDTO(ChaveJogo chaveJogo) {
		
		ChaveJogoResponseDTO chaveJogoResponseDTO = new ChaveJogoResponseDTO(chaveJogo.getId(), chaveJogo.getChave(), chaveJogo.getChaveStatus(), chaveJogo.getJogo());
		
		return chaveJogoResponseDTO;
	}
}
