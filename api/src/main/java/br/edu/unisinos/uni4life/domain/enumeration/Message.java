package br.edu.unisinos.uni4life.domain.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Message {

    //D
    DATA_INVALIDA("data.invalida"),
    DATA_NASCIMENTO_INVALIDA("data-nascimento.invalida"),

    //S
    SERVICO_NAO_IMPLEMENADO("servico.nao-implementado"),

    //F
    FALHA_INESPERADA("falha.inesperada"),

    //U
    USUARIO_NAO_ENCONTRADO("usuario.nao.encontrado");

    private final String message;
}
