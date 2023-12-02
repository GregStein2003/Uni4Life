package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.dto.InformacoesExtraConteudo;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;

public class ConteudoResponseMapper {

    public ConteudoResponse apply(final ConteudoEnitity enitity, final String imagemConteudoBase64,
        final InformacoesExtraConteudo informacoes) {
        if (isNull(enitity) || isNull(informacoes)) {
            return null;
        }

        return ConteudoResponse.builder()
            .id(enitity.getId())
            .descricao(enitity.getDescricao())
            .autor(enitity.getAutor().getNome())
            .imagemAutor(
                isNotBlank(informacoes.getImagemAutorBase64()) ? informacoes.getImagemAutorBase64() : null
            )
            .titulo(enitity.getTitulo())
            .link(enitity.getLink())
            .tipoConteudo(enitity.getTipo())
            .favoritado(informacoes.isFavorito())
            .curtido(informacoes.isCurtido())
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
