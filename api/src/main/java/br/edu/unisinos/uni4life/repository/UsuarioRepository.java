package br.edu.unisinos.uni4life.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity, UUID> {

    boolean existsByEmail(final String email);

    Optional<UsuarioEntity> findByEmail(final String email);

    @Transactional(readOnly = true)
    @Query("SELECT U as usuario, "
        + "CASE WHEN S.seguidor.id = :idUsuario THEN true ELSE false END AS seguido "
        + "FROM UsuarioEntity U "
        + "LEFT JOIN SeguidorEntity S ON U.id = S.seguido.id "
        + "WHERE (S.seguidor.id = :idUsuario OR S.seguidor.id IS NULL) AND U.id != :idUsuario")
    Page<Map<String, Object>> findUsuariosToFollow(@Param("idUsuario") final UUID idUsuario, final Pageable paginacao);


    @Modifying
    @Query("UPDATE UsuarioEntity u "
        + "SET u.quantidadeSeguidores = u.quantidadeSeguidores + :quantidade WHERE u.id = :idUsuario")
    void updateQuantidadeSeguidoresById(@Param("quantidade") final Long quantidade,
        @Param("idUsuario") final UUID idUsuario);

}
