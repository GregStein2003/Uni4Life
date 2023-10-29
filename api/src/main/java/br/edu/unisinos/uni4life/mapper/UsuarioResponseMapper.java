package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;

import java.util.function.Function;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;

public class UsuarioResponseMapper implements Function<UsuarioEntity, UsuarioResponse> {

    @Override
    public UsuarioResponse apply(final UsuarioEntity entity) {
        if (isNull(entity)) {
            return null;
        }

        return UsuarioResponse.builder()
            .id(entity.getId())
            .nome(entity.getNome())
            .email(entity.getEmail())
            .registroAcademico(entity.getRegistroAcademico())
            .telefone(entity.getTelefone())
            .dataNascimento(entity.getDataNascimento())
            .tipoConta(entity.getTipo())
            .build();
    }
}
