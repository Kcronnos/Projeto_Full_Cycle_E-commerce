package br.com.tatifurtando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tatifurtando.entidades.ChaveJogo;

@Repository
public interface ChaveJogoRepository extends JpaRepository<ChaveJogo, Long>{

}
