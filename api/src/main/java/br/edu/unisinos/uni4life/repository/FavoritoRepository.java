package br.edu.unisinos.uni4life.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.FavoritoEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public interface FavoritoRepository extends CrudRepository<FavoritoEntity, UUID> {

    boolean existsByUsuarioAndConteudo(final UsuarioEntity usuario, final ConteudoEnitity conteudo);

    boolean existsByUsuarioIdAndConteudo(final UUID idUsuario, final ConteudoEnitity conteudo);

    void deleteByUsuarioIdAndConteudo(final UUID idUsuario, final ConteudoEnitity ConteudoEnitity);

}
