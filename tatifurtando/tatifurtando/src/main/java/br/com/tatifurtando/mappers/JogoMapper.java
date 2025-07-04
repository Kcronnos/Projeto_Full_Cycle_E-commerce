package br.com.tatifurtando.mappers;

import br.com.tatifurtando.dtos.JogoCreateDTO;
import br.com.tatifurtando.dtos.JogoResponseDTO;
import br.com.tatifurtando.entidades.Jogo;

public class JogoMapper {

public static Jogo toEntity(JogoCreateDTO jogoCreateDTO) {
		
		Jogo jogo = new Jogo();
		jogo.setNome(jogoCreateDTO.nome());
		jogo.setDescricao(jogoCreateDTO.descricao());
		jogo.setPreco(jogoCreateDTO.preco());
		jogo.setDesenvolvedora(jogo.getDesenvolvedora());
		jogo.setImagemUrl(jogoCreateDTO.imagemurl());
		
		return jogo;
	}
	
	public static JogoResponseDTO toDTO(Jogo jogo) {
		
		JogoResponseDTO jogoResponse = new JogoResponseDTO(jogo.getId(), jogo.getNome(), jogo.getDescricao(), jogo.getPreco(), jogo.getDesenvolvedora(), jogo.getImagemUrl());
		
		return jogoResponse;
	}
}
