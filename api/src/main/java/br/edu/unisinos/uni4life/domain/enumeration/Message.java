package br.edu.unisinos.uni4life.domain.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Message {

    //C
    COMENTARIO_NAO_ENCONTRADO("comentario.nao.encontrado"),
    CONTEUDO_NAO_ENCONTRADO("conteudo.nao.encontrado"),
    CONTEUDO_JA_CURTIDO("conteudo.ja.curtido"),
    CONTEUDO_JA_FAVORITADO("conteudo.ja.favoritado"),

    //D
    DATA_INVALIDA("data.invalida"),
    DATA_NASCIMENTO_INVALIDA("data-nascimento.invalida"),

    //E
    EMAIL_CADASTRADO("email.cadastrado"),
    EMAIL_INVALIDO("email.invalido"),

    //F
    FALHA_INESPERADA("falha.inesperada"),

    //I
    IMAGEM_INVALIDA("imagem.invalida"),
    IMAGEM_CONTEUDO_INVALIDA("imagem.conteudo.invalido"),
    IMAGEM_USUARIO_INVALIDA("imagem.usuario.invalida"),

    //L
    LINK_CONTEUDO_INVALIDO("link-conteudo.invalido"),

    //N
    NAO_PERMITIDO_SEGUIR_VOCE("nao-permitido.seguir.voce"),

    //R
    REQUISICAO_INVALIDA("requisicao.invalida"),

    //S
    SERVICO_NAO_IMPLEMENADO("servico.nao-implementado"),

    //T
    TELEFONE_INVALIDO("telefone.invalido"),

    //U
    USUARIIO_JA_SEGUIDO("usuario.ja.seguido"),
    USUARIO_NAO_AUTOR_COMENTARIO("usuario.nao.autor-comentario"),
    USUARIO_NAO_ENCONTRADO("usuario.nao.encontrado");

    private final String message;
}
