package br.edu.unisinos.uni4life.mapper.entity;

import static java.util.Objects.isNull;

import java.util.Objects;
import java.util.function.BiFunction;

import br.edu.unisinos.uni4life.domain.entity.SeguidorEntity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public class SeguidorEntityMapper implements BiFunction<UsuarioEntity, UsuarioEntity, SeguidorEntity> {

    @Override
    public SeguidorEntity apply(final UsuarioEntity seguido, final UsuarioEntity seguidor) {
        if (isNull(seguido) || isNull(seguidor)) {
            return null;
        }

        final SeguidorEntity entity = new SeguidorEntity();
        entity.setSeguido(seguido);
        entity.setSeguidor(seguidor);

        return entity;
    }
}
