package br.edu.unisinos.uni4life.domain.enumeration.usuario;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Segmento {

    ACADEMICO("ROLE_ACADEMICO"),
    ADMINISTRADOR("ROLE_ADMINISTRADOR");

    private final String role;

}
