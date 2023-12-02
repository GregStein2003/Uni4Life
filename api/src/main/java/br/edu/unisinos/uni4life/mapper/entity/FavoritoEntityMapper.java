package br.edu.unisinos.uni4life.mapper.entity;

import static java.util.Objects.isNull;

import java.util.function.BiFunction;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.FavoritoEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public class FavoritoEntityMapper implements BiFunction<UsuarioEntity, ConteudoEnitity, FavoritoEntity> {

    @Override
    public FavoritoEntity apply(final UsuarioEntity usuario, final ConteudoEnitity conteudo) {

        if (isNull(usuario) || isNull(conteudo)) {
            return null;
        }

        final FavoritoEntity entity = new FavoritoEntity();

        entity.setUsuario(usuario);
        entity.setConteudo(conteudo);

        return entity;
    }
}
