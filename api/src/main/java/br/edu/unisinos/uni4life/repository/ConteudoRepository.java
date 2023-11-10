package br.edu.unisinos.uni4life.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;

public interface ConteudoRepository extends CrudRepository<ConteudoEnitity, UUID> {

    Page<ConteudoEnitity> findByAutorId(final UUID idAutor, final Pageable pageable);

    @Query(value = "SELECT C FROM ConteudoEnitity C "
        + "INNER JOIN UsuarioEntity U ON U = C.autor "
        + "INNER JOIN SeguidorEntity S ON S.seguido = U "
        + "WHERE S.seguidor.id = :idSeguidor AND U.tipo = 'PUBLICA'")
    Page<ConteudoEnitity> findConteudoSeguido(@Param("idSeguidor") final UUID idSeguidor, final Pageable paginacao);
}
