package br.com.tatifurtando.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tatifurtando.dtos.ChaveJogoResponseDTO;
import br.com.tatifurtando.dtos.ItemPedidoResponseDTO;
import br.com.tatifurtando.entidades.ChaveJogo;
import br.com.tatifurtando.entidades.ItemPedido;
import br.com.tatifurtando.entidades.Jogo;
import br.com.tatifurtando.enuns.ChaveStatus;
import br.com.tatifurtando.mappers.ChaveJogoMapper;
import br.com.tatifurtando.repositories.ChaveJogoRepository;
import br.com.tatifurtando.repositories.ItemPedidoRepository;

@Service
public class ChaveJogoService {

	@Autowired
	ChaveJogoRepository chaveJogoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public void store(ItemPedidoResponseDTO itemPedidoResponseDTO, Jogo jogo) {

	    ItemPedido itemPedido = itemPedidoRepository.findById(itemPedidoResponseDTO.id())
	        .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado com ID: " + itemPedidoResponseDTO.id()));

	    boolean chaveExiste = chaveJogoRepository.existsByItemPedido(itemPedido);

	    if (chaveExiste) {
	        System.out.println("Chave já existe para o itemPedido " + itemPedido.getId());
	        return;
	    }

	    String chave = UUID.randomUUID().toString();

	    chaveJogoRepository.save(new ChaveJogo(chave, ChaveStatus.DISPONIVEL, jogo, itemPedido));
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
	
	public List<ChaveJogoResponseDTO> listarPorItemPedido(long idItemPedido) {
	    List<ChaveJogo> chaves = chaveJogoRepository.findByItemPedidoId(idItemPedido);
	    return chaves.stream().map(ChaveJogoMapper::toDTO).toList();
	}
}
