package br.edu.unisinos.uni4life.mapper.entity;

import static java.util.Objects.isNull;

import java.util.function.BiFunction;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.CurtidaEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public class CurtidaEntityMapper implements BiFunction<UsuarioEntity, ConteudoEnitity, CurtidaEntity> {

    @Override
    public CurtidaEntity apply(final UsuarioEntity usuario, final ConteudoEnitity conteudo) {

        if (isNull(usuario) || isNull(conteudo)) {
            return null;
        }

        final CurtidaEntity entity = new CurtidaEntity();

        entity.setUsuario(usuario);
        entity.setConteudo(conteudo);

        return entity;
    }

}
