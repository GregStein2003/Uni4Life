package br.edu.unisinos.uni4life.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public final class Pagina<T> implements Serializable {

    private static final long serialVersionUID = 9164736326255366969L;

    private List<T> conteudo;
    private Integer numeroPagina;
    private Integer elementosPagina;
    private Long elementosTotais;
    private boolean ultimaPagina;

    public Pagina(final Page<T> pagina) {
        this.conteudo = pagina.getContent();
        this.numeroPagina = pagina.getNumber() + 1;
        this.elementosPagina = pagina.getNumberOfElements();
        this.elementosTotais = pagina.getTotalElements();
        this.ultimaPagina = pagina.isLast();
    }
}
