package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;

import java.util.function.BiFunction;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraConteudoRequest;

public class ConteudoEntityMapper implements BiFunction<CadastraConteudoRequest, UsuarioEntity, ConteudoEnitity> {

    @Override
    public ConteudoEnitity apply(final CadastraConteudoRequest request, final UsuarioEntity autor) {
        if (isNull(request)) {
            return null;
        }

        final ConteudoEnitity enitity = new ConteudoEnitity();

        enitity.setTitulo(request.getTitulo());
        enitity.setDescricao(request.getDescricao());
        enitity.setTipo(request.getTipoConteudo());
        enitity.setLink(request.getLink());
        enitity.setAutor(autor);

        return enitity;
    }
}
