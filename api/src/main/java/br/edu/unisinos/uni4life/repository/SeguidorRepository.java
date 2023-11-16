package br.edu.unisinos.uni4life.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.unisinos.uni4life.domain.entity.SeguidorEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public interface SeguidorRepository extends JpaRepository<SeguidorEntity, UUID> {

    boolean existsSeguidorEntityBySeguidorAndSeguido(final UsuarioEntity seguidor, final UsuarioEntity seguido);

    boolean existsSeguidorEntityBySeguidorIdAndSeguido(final UUID idSeguidor, final UsuarioEntity seguido);

    @Query("SELECT s FROM SeguidorEntity s WHERE s.seguido.id = :idSeguido")
    Page<SeguidorEntity> findAllSeguidoresByIdSeguido(@Param("idSeguido") final UUID idSeguido,
        final Pageable paginacao);

    @Query("SELECT s FROM SeguidorEntity s WHERE s.seguidor.id = :idSeguidor")
    Page<SeguidorEntity> findAllSeguidosByIdSeguidor(@Param("idSeguidor") final UUID idSeguidor,
        final Pageable paginacao);

    @Modifying
    @Query("DELETE FROM SeguidorEntity s WHERE s.seguido.id= :idSeguido AND s.seguidor.id= :idSeguidor")
    int delete(@Param("idSeguido") final UUID idSeguido, @Param("idSeguidor") final UUID idSeguidor);

}
