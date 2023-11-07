package br.edu.unisinos.uni4life.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.unisinos.uni4life.domain.enumeration.conteudo.TipoConteudo;

public class TipoConteudoValidator implements ConstraintValidator<TipoConteudoValido, TipoConteudo> {

    private TipoConteudo[] subset;

    @Override
    public void initialize(final TipoConteudoValido constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(final TipoConteudo valor, final ConstraintValidatorContext context) {
        return valor != null && Arrays.asList(subset).contains(valor);
    }
}
