package br.edu.unisinos.uni4life.domain.enumeration;

import static br.edu.unisinos.uni4life.domain.enumeration.Message.DATA_INVALIDA;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.DATA_NASCIMENTO_INVALIDA;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamposData {

    DATA_NASCIMENTO("dataNascimento", DATA_NASCIMENTO_INVALIDA);

    private final String campo;
    private final Message message;

    public static Message retornaMensagemErro(final String codigo) {
        return Arrays.stream(CamposData.values())
            .filter(campo -> campo.getCampo().equals(codigo))
            .map(CamposData::getMessage)
            .findFirst()
            .orElse(DATA_INVALIDA);
    }
}
