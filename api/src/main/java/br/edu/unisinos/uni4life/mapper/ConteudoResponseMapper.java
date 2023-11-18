package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;

public class ConteudoResponseMapper {

    public ConteudoResponse apply(final ConteudoEnitity enitity,
        final String imagemConteudoBase64, final String imagemAutorBase64) {
        if (isNull(enitity)) {
            return null;
        }

        return ConteudoResponse.builder()
            .id(enitity.getId())
            .descricao(enitity.getDescricao())
            .autor(enitity.getAutor().getNome())
            .imagemAutor(isNotBlank(imagemAutorBase64) ? imagemAutorBase64 : null)
            .titulo(enitity.getTitulo())
            .link(enitity.getLink())
            .tipoConteudo(enitity.getTipo())
            .dataCriacao(ofNullable(enitity.getDataCriacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .dataAtualizacao(ofNullable(enitity.getDataAtualizacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .imagem(isNotBlank(imagemConteudoBase64) ? imagemConteudoBase64 : null)
            .build();
    }
}
