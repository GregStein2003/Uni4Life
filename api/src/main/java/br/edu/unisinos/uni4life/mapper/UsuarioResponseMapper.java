package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;

public class UsuarioResponseMapper {

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
            .segmento(entity.getSegmento())
            .seguidores(entity.getQuantidadeSeguidores())
            .build();
    }

    public UsuarioResponse apply(final UsuarioEntity entity,
        final LocalDateTime dataInicioRelacionamento) {

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
            .build();
    }
}
