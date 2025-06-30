package br.com.tatifurtando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tatifurtando.entidades.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{

}
