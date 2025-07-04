package br.com.tatifurtando.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tatifurtando.entidades.Pedido;
import br.com.tatifurtando.enuns.PedidoStatus;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	List<Pedido> findByUserIdAndStatus(Long userId, PedidoStatus status);
}