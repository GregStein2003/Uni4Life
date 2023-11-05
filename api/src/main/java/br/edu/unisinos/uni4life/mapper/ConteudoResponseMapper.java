package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.time.LocalDateTime;
import java.util.function.Function;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;

public class ConteudoResponseMapper implements Function<ConteudoEnitity, ConteudoResponse> {

    @Override
    public ConteudoResponse apply(final ConteudoEnitity enitity) {
        if (isNull(enitity)) {
            return null;
        }

        return ConteudoResponse.builder()
            .id(enitity.getId())
            .descricao(enitity.getDescricao())
            .autor(enitity.getAutor().getNome())
            .titulo(enitity.getTitulo())
            .link(enitity.getLink())
            .tipoConteudo(enitity.getTipo())
            .dataCriacao(ofNullable(enitity.getDataCriacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .dataAtualizacao(ofNullable(enitity.getDataAtualizacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .build();
    }
}
