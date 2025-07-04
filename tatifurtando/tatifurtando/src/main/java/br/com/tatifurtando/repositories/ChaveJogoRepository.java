package br.com.tatifurtando.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tatifurtando.entidades.ChaveJogo;
import br.com.tatifurtando.entidades.ItemPedido;

@Repository
public interface ChaveJogoRepository extends JpaRepository<ChaveJogo, Long>{
	
	List<ChaveJogo> findByItemPedidoId(Long itemPedidoId);
	boolean existsByItemPedido(ItemPedido itemPedido);
}
