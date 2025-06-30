package br.com.tatifurtando.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tatifurtando.dtos.ItemPedidoCreateDTO;
import br.com.tatifurtando.dtos.ItemPedidoResponseDTO;
import br.com.tatifurtando.entidades.ItemPedido;
import br.com.tatifurtando.mappers.ItemPedidoMapper;
import br.com.tatifurtando.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	
	public ItemPedidoResponseDTO store(ItemPedidoCreateDTO itemPedidoCreateDTO) {
		
		ItemPedido itemPedido = ItemPedidoMapper.toEntity(itemPedidoCreateDTO);
				
		return ItemPedidoMapper.toDTO(itemPedidoRepository.save(itemPedido));
	}
	
	public List<ItemPedidoResponseDTO> list(){
		return itemPedidoRepository.findAll().stream().map(ItemPedidoMapper::toDTO).toList();
	}
		
	public ItemPedidoResponseDTO show(long id) {
		ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Item Peidido não encontrado"));
		
		return ItemPedidoMapper.toDTO(itemPedido);
	}
	
	public void destroy(long id) {
		ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Item Pedido não encontrado"));
		
		itemPedidoRepository.delete(itemPedido);
	}
}
