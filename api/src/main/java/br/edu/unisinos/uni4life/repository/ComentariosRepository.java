package br.edu.unisinos.uni4life.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import br.edu.unisinos.uni4life.domain.entity.ComentarioEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public interface ComentariosRepository extends CrudRepository<ComentarioEntity, UUID> {

    Page<ComentarioEntity> findByAutorId(final UUID idAutor, final Pageable paginacao);

    Page<ComentarioEntity> findByConteudoId(final UUID idConteudo, final Pageable paginacao);

    boolean existsByIdAndAutor(final UUID idComtentario, final UsuarioEntity autor);

}
