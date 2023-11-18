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

    boolean existsByEmailAndIdIsNot(final String email, final UUID uuid);

    Optional<UsuarioEntity> findByEmail(final String email);

    @Transactional(readOnly = true)
    @Query("SELECT U FROM UsuarioEntity U WHERE U.id != :idUsuario")
    Page<UsuarioEntity> findUsuariosToFollow(@Param("idUsuario") final UUID idUsuario, final Pageable paginacao);


    @Modifying
    @Query("UPDATE UsuarioEntity u "
        + "SET u.quantidadeSeguidores = u.quantidadeSeguidores + :quantidade WHERE u.id = :idUsuario")
    void updateQuantidadeSeguidoresById(@Param("quantidade") final Long quantidade,
        @Param("idUsuario") final UUID idUsuario);

}
