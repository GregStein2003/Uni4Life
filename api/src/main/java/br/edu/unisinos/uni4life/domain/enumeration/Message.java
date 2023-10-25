package br.edu.unisinos.uni4life.domain.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Message {

    SERVICO_NAO_IMPLEMENADO("servico.nao-implementado"),
    FALHA_INESPERADA("falha.inesperada");

    private final String message;
}
