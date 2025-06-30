package br.com.tatifurtando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tatifurtando.entidades.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
