package br.edu.unisinos.uni4life.mapper.entity;

import static java.util.Objects.isNull;

import br.edu.unisinos.uni4life.domain.entity.ComentarioEntity;
import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraComentarioRequest;

public class ComentarioEntityMapper {

    public ComentarioEntity apply(final CadastraComentarioRequest request,
        final UsuarioEntity usuario, final ConteudoEnitity conteudo) {

        if (isNull(request) || isNull(usuario) || isNull(conteudo)) {
            return null;
        }

        final ComentarioEntity entity = new ComentarioEntity();

        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setAutor(usuario);
        entity.setConteudo(conteudo);

        return entity;
    }


}
