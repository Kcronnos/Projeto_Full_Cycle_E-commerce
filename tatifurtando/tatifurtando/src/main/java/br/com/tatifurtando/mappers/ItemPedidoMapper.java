package br.com.tatifurtando.mappers;

import br.com.tatifurtando.dtos.ItemPedidoCreateDTO;
import br.com.tatifurtando.dtos.ItemPedidoResponseDTO;
import br.com.tatifurtando.entidades.ItemPedido;

public class ItemPedidoMapper {

	public static ItemPedido toEntity(ItemPedidoCreateDTO itemPedidoCreateDTO) {
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setQuantidade(itemPedidoCreateDTO.quantidade());
		itemPedido.setPrecoItems(itemPedidoCreateDTO.precoItems());
		itemPedido.setPedido(itemPedidoCreateDTO.pedido());
		itemPedido.setJogo(itemPedidoCreateDTO.jogo());
		
		return itemPedido;
	}
	
	public static ItemPedidoResponseDTO toDTO(ItemPedido itemPedido) {
		
		ItemPedidoResponseDTO itemPedidoResponse = new ItemPedidoResponseDTO(itemPedido.getId(), itemPedido.getQuantidade(), itemPedido.getPrecoItems(), itemPedido.getPedido(), itemPedido.getJogo());
		
		return itemPedidoResponse;
	}
}
