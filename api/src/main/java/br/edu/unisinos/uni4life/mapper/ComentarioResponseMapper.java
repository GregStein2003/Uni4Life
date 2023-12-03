package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;

import br.edu.unisinos.uni4life.domain.entity.ComentarioEntity;
import br.edu.unisinos.uni4life.dto.response.ComentarioResponse;

public class ComentarioResponseMapper {


    public ComentarioResponse apply(final ComentarioEntity entity,
        final String imagemAutorBase64, final boolean tituloConteudo) {

        if (isNull(entity)) {
            return null;
        }

        return ComentarioResponse.builder()
            .id(entity.getId())
            .titulo(entity.getTitulo())
            .descricao(entity.getDescricao())
            .dataCriacao(ofNullable(entity.getDataCriacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .dataAtualizacao(ofNullable(entity.getDataAtualizacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .idAutor(entity.getAutor().getId())
            .autor(entity.getAutor().getNome())
            .imagemAutor(
                isNotBlank(imagemAutorBase64) ? imagemAutorBase64 : null
            )
            .tituloConteudo(
                tituloConteudo ? entity.getConteudo().getTitulo() : null
            )
            .build();
    }

    public ComentarioResponse apply(final ComentarioEntity entity,
        final String imagemAutorBase64) {
        return this.apply(entity, imagemAutorBase64, false);
    }
}
