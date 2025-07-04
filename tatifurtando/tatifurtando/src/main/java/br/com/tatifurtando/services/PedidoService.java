package br.com.tatifurtando.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tatifurtando.dtos.ItemPedidoResponseDTO;
import br.com.tatifurtando.dtos.PedidoCreateDTO;
import br.com.tatifurtando.dtos.PedidoResponseDTO;
import br.com.tatifurtando.entidades.Pedido;
import br.com.tatifurtando.entidades.User;
import br.com.tatifurtando.enuns.PedidoStatus;
import br.com.tatifurtando.mappers.PedidoMapper;
import br.com.tatifurtando.repositories.PedidoRepository;
import br.com.tatifurtando.repositories.UserRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@Autowired
	private ChaveJogoService chaveJogoService;
	
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
	
	public List<PedidoResponseDTO> listByUserIdAndStatus(Long userId, PedidoStatus status) {
	    List<Pedido> pedidos = pedidoRepository.findByUserIdAndStatus(userId, status);
	    return pedidos.stream()
	                  .map(PedidoMapper::toDTO)
	                  .toList();
	}
	
	public List<Pedido> listPedidosStatusPago(Long userId, PedidoStatus status) {
		List<Pedido> pedidosPagos = pedidoRepository.findByUserIdAndStatus(userId, PedidoStatus.PAGO);
		return pedidosPagos;
	}
	
	@Transactional
	public List<PedidoResponseDTO> getOrCreatePedidosPendentes(Long userId) {
	    List<Pedido> pedidosPendentes = pedidoRepository.findByUserIdAndStatus(userId, PedidoStatus.PENDENTE);
	    
	    User user = userRepository.findById(userId)
	    	    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	    
	    if (pedidosPendentes.isEmpty()) {
	        Pedido novoPedido = new Pedido();
	        novoPedido.setUser(user);
	        novoPedido.setStatus(PedidoStatus.PENDENTE);
	        novoPedido.setDataCriado(new Date(System.currentTimeMillis()));
	        novoPedido.setTotal(BigDecimal.ZERO);

	        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
	        pedidosPendentes = List.of(pedidoSalvo);
	    }
	    
	    return pedidosPendentes.stream()
	            .map(PedidoMapper::toDTO)
	            .toList();
	}
	
	public void finalizarPedido(Pedido pedido) {
	    try {
	    	List<ItemPedidoResponseDTO> itens = itemPedidoService.listarPorPedido(pedido.getId())
	    		    .stream()
	    		    .distinct()
	    		    .toList();

	        BigDecimal total = BigDecimal.ZERO;

	        for (ItemPedidoResponseDTO itemPedido : itens) {
	        	System.out.println("Processando ItemPedido ID: " + itemPedido.id());
	            BigDecimal itemTotal = itemPedido.precoItems().multiply(BigDecimal.valueOf(itemPedido.quantidade()));
	            total = total.add(itemTotal);

	            for (int i = 0; i < itemPedido.quantidade(); i++) {
	            	
	                chaveJogoService.store(itemPedido, itemPedido.jogo());
	            }
	        }

	        pedido.setStatus(PedidoStatus.PAGO);
	        pedido.setDataCriado(new Date(System.currentTimeMillis()));
	        pedido.setTotal(total);
	        pedidoRepository.save(pedido);
	    } catch (Exception e) {
	        System.err.println("Erro ao finalizar pedido: " + e.getMessage());
	        e.printStackTrace();
	        throw e; // propaga para o controller retornar 500 com log
	    }
	}

}
