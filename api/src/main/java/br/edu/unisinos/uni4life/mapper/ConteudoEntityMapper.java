package br.edu.unisinos.uni4life.mapper;

import static java.util.Objects.isNull;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraConteudoRequest;

public class ConteudoEntityMapper {

    public ConteudoEnitity apply(final CadastraConteudoRequest request,
        final UsuarioEntity autor, final String arquivoImagem) {
        if (isNull(request)) {
            return null;
        }

        final ConteudoEnitity enitity = new ConteudoEnitity();

        enitity.setTitulo(request.getTitulo());
        enitity.setDescricao(request.getDescricao());
        enitity.setTipo(request.getTipoConteudo());
        enitity.setLink(request.getLink());
        enitity.setAutor(autor);
        enitity.setImagem(arquivoImagem);

        return enitity;
    }
}
