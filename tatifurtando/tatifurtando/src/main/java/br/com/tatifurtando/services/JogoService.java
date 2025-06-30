package br.com.tatifurtando.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tatifurtando.dtos.JogoCreateDTO;
import br.com.tatifurtando.dtos.JogoResponseDTO;
import br.com.tatifurtando.dtos.RawgDetalheDTO;
import br.com.tatifurtando.dtos.RawgJogoDTO;
import br.com.tatifurtando.dtos.RawgResponseDTO;
import br.com.tatifurtando.entidades.Jogo;
import br.com.tatifurtando.mappers.JogoMapper;
import br.com.tatifurtando.repositories.JogoRepository;

@Service
public class JogoService {

	@Autowired
	JogoRepository jogoRepository;
	
	
	public JogoResponseDTO store(JogoCreateDTO jogoCreateDTO) {
		
		Jogo jogo = JogoMapper.toEntity(jogoCreateDTO);
				
		return JogoMapper.toDTO(jogoRepository.save(jogo));
	}
	
	public List<JogoResponseDTO> list(){
		return jogoRepository.findAll().stream().map(JogoMapper::toDTO).toList();
	}
		
	public JogoResponseDTO show(long id) {
		Jogo jogo = jogoRepository.findById(id).orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
		
		return JogoMapper.toDTO(jogo);
	}
	
	public void destroy(long id) {
		Jogo jogo = jogoRepository.findById(id).orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
		
		jogoRepository.delete(jogo);
	}
	
	public void importarJogosPopularesComDetalhes() {
	    RestTemplate restTemplate = new RestTemplate();
	    Random random = new Random();
	    String API_KEY = "ffd12ad7feaf4bddbb1cd13286641628";

	    int totalJogos = 100;
	    int porPagina = 40;
	    int paginas = (int) Math.ceil((double) totalJogos / porPagina);

	    for (int page = 1; page <= paginas; page++) {
	        String url = "https://api.rawg.io/api/games?key=" + API_KEY + "&page_size=" + porPagina + "&page=" + page;
	        ResponseEntity<RawgResponseDTO> response = restTemplate.getForEntity(url, RawgResponseDTO.class);

	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            List<Jogo> jogos = new ArrayList<>();

	            for (RawgJogoDTO jogoApi : response.getBody().results()) {
	                try {
	                    // Chamada para detalhes do jogo
	                    String detalheUrl = "https://api.rawg.io/api/games/" + jogoApi.id() + "?key=" + API_KEY;
	                    ResponseEntity<RawgDetalheDTO> detalheResponse = restTemplate.getForEntity(detalheUrl, RawgDetalheDTO.class);

	                    if (detalheResponse.getStatusCode().is2xxSuccessful() && detalheResponse.getBody() != null) {
	                        RawgDetalheDTO detalhe = detalheResponse.getBody();

	                        Jogo jogo = new Jogo();
	                        jogo.setNome(detalhe.name());
	                        jogo.setDescricao(detalhe.description_raw());
	                        jogo.setImagemUrl(detalhe.background_image());
	                        jogo.setDesenvolvedora(
	                            detalhe.developers() != null && !detalhe.developers().isEmpty()
	                                ? detalhe.developers().get(0).name()
	                                : "Desenvolvedora Desconhecida"
	                        );

	                        // Preço aleatório entre 60.00 e 250.00
	                        BigDecimal preco = BigDecimal.valueOf(60 + (190 * random.nextDouble()));
	                        jogo.setPreco(preco.setScale(2, RoundingMode.HALF_UP));

	                        jogos.add(jogo);
	                    }

	                } catch (Exception e) {
	                    System.out.println("Erro ao buscar detalhes do jogo ID " + jogoApi.id() + ": " + e.getMessage());
	                }
	            }

	            jogoRepository.saveAll(jogos);
	        }
	    }
	}
}
