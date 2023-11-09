package br.edu.unisinos.uni4life.domain.enumeration.conteudo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TipoConteudo {

    LIVRO(false),
    MUSICA(true),
    PODCAST(true),
    TEXTO(false),
    VIDEO(true);

    private final boolean precisaLink;
}
