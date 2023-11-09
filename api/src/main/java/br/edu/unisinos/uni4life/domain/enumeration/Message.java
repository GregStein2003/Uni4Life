package br.edu.unisinos.uni4life.domain.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Message {

    //C
    CONTEUDO_NAO_ENCONTRADO("conteudo.nao.encontrado"),

    //D
    DATA_INVALIDA("data.invalida"),
    DATA_NASCIMENTO_INVALIDA("data-nascimento.invalida"),

    //E
    EMAIL_CADASTRADO("email.cadastrado"),

    //L
    LINK_CONTEUDO_INVALIDO("link-conteudo.invalido"),

    //N
    NAO_PERMITIDO_SEGUIR_VOCE("nao-permitido.seguir.voce"),

    //S
    SERVICO_NAO_IMPLEMENADO("servico.nao-implementado"),

    //F
    FALHA_INESPERADA("falha.inesperada"),

    //U
    USUARIIO_JA_SEGUIDO("usuario.ja.seguido"),
    USUARIO_NAO_ENCONTRADO("usuario.nao.encontrado");

    private final String message;
}
