package br.edu.unisinos.uni4life.dto;

import java.io.Serializable;
import java.util.List;

import br.edu.unisinos.uni4life.dto.response.ComentarioResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class InformacoesExtraConteudo implements Serializable {

    private static final long serialVersionUID = 1880871797266112081L;

    private final String imagemAutorBase64;
    private final boolean isFavorito;
    private final boolean isCurtido;
    private final List<ComentarioResponse> comentarios;

}
