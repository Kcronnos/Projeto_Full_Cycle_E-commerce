package br.com.tatifurtando.mappers;

import br.com.tatifurtando.dtos.PedidoCreateDTO;
import br.com.tatifurtando.dtos.PedidoResponseDTO;
import br.com.tatifurtando.entidades.Pedido;

public class PedidoMapper {

	public static Pedido toEntity(PedidoCreateDTO pedidoCreateDTO) {
		
		Pedido pedido = new Pedido();
		pedido.setStatus(pedidoCreateDTO.pedidoStatus());
		pedido.setTotal(pedidoCreateDTO.total());
		pedido.setUser(pedidoCreateDTO.user());
		
		return pedido;
	}
	
	public static PedidoResponseDTO toDTO(Pedido pedido) {
		
		PedidoResponseDTO pedidoResponse = new PedidoResponseDTO(pedido.getId(), pedido.getStatus(), pedido.getTotal(), pedido.getDataCridado(), pedido.getUser());
		
		return pedidoResponse;
	}
}
