package br.com.tatifurtando.dtos;

import java.util.List;

public record RawgDetalheDTO(
		int id,
	    String name,
	    String description_raw,
	    String background_image,
	    List<DesenvolvedoraDTO> developers) {}
