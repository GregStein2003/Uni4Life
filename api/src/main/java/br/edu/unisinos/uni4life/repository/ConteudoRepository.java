package br.edu.unisinos.uni4life.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;

public interface ConteudoRepository extends CrudRepository<ConteudoEnitity, UUID> {

    Page<ConteudoEnitity> findByAutorId(final UUID idAutor, final Pageable pageable);
}
