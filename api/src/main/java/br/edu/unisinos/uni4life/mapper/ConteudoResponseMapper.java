package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDateTime;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.dto.InformacoesExtraConteudo;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;

public class ConteudoResponseMapper {

    public ConteudoResponse apply(final ConteudoEnitity entity, final String imagemConteudoBase64,
        final InformacoesExtraConteudo informacoes) {
        if (isNull(entity) || isNull(informacoes)) {
            return null;
        }

        return ConteudoResponse.builder()
            .id(entity.getId())
            .descricao(entity.getDescricao())
            .autor(entity.getAutor().getNome())
            .imagemAutor(
                isNotBlank(informacoes.getImagemAutorBase64()) ? informacoes.getImagemAutorBase64() : null
            )
            .titulo(entity.getTitulo())
            .link(entity.getLink())
            .tipoConteudo(entity.getTipo())
            .favoritado(informacoes.isFavorito())
            .curtido(informacoes.isCurtido())
            .dataCriacao(ofNullable(entity.getDataCriacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .dataAtualizacao(ofNullable(entity.getDataAtualizacao())
                .map(LocalDateTime::toLocalDate)
                .orElse(null))
            .imagem(isNotBlank(imagemConteudoBase64) ? imagemConteudoBase64 : null)
            .comentarios(informacoes.getComentarios())
            .build();
    }
}
