package br.edu.unisinos.uni4life.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.edu.unisinos.uni4life.domain.enumeration.usuario.TipoConta;

public class TipoContaValidator implements ConstraintValidator<TipoContaValido, TipoConta> {

    private TipoConta[] subset;

    @Override
    public void initialize(final TipoContaValido constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(final TipoConta valor, final ConstraintValidatorContext context) {
        return valor != null && Arrays.asList(subset).contains(valor);
    }
}
