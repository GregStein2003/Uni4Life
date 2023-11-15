package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;

public class UsuarioResponseMapper {

    public UsuarioResponse apply(final UsuarioEntity entity, final String imagemBase64) {
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
            .segmento(entity.getSegmento())
            .seguidores(entity.getQuantidadeSeguidores())
            .imagem(isNotBlank(imagemBase64) ? imagemBase64 : null)
            .build();
    }

    public UsuarioResponse apply(final UsuarioEntity usuario, final boolean isSeguido,
        final String imagemBase64) {

        if (isNull(usuario)) {
            return null;
        }

        return UsuarioResponse.builder()
            .id(usuario.getId())
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .registroAcademico(usuario.getRegistroAcademico())
            .telefone(usuario.getTelefone())
            .dataNascimento(usuario.getDataNascimento())
            .tipoConta(usuario.getTipo())
            .segmento(usuario.getSegmento())
            .seguidores(usuario.getQuantidadeSeguidores())
            .seguido(isSeguido)
            .imagem(isNotBlank(imagemBase64) ? imagemBase64 : null)
            .build();
    }

    public UsuarioResponse apply(final UsuarioEntity entity,
        final String imagemBase64, final LocalDateTime dataInicioRelacionamento) {

        if (isNull(entity) || isNull(dataInicioRelacionamento)) {
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
            .segmento(entity.getSegmento())
            .seguidores(entity.getQuantidadeSeguidores())
            .dataRelacionamento(dataInicioRelacionamento.toLocalDate())
            .imagem(isNotBlank(imagemBase64) ? imagemBase64 : null)
            .build();
    }
}
