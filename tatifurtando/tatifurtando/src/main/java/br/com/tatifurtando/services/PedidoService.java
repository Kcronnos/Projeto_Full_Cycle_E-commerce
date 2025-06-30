package br.com.tatifurtando.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tatifurtando.dtos.PedidoCreateDTO;
import br.com.tatifurtando.dtos.PedidoResponseDTO;
import br.com.tatifurtando.entidades.Pedido;
import br.com.tatifurtando.mappers.PedidoMapper;
import br.com.tatifurtando.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	
	public PedidoResponseDTO store(PedidoCreateDTO pedidoCreateDTO) {
		
		Pedido pedido = PedidoMapper.toEntity(pedidoCreateDTO);
				
		return PedidoMapper.toDTO(pedidoRepository.save(pedido));
	}
	
	public List<PedidoResponseDTO> list(){
		return pedidoRepository.findAll().stream().map(PedidoMapper::toDTO).toList();
	}
		
	public PedidoResponseDTO show(long id) {
		Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Peidido não encontrado"));
		
		return PedidoMapper.toDTO(pedido);
	}
	
	public void destroy(long id) {
		Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
		
		pedidoRepository.delete(pedido);
	}
}
