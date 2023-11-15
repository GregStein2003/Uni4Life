package br.edu.unisinos.uni4life.mapper;

import static br.edu.unisinos.uni4life.domain.enumeration.usuario.Segmento.ACADEMICO;
import static br.edu.unisinos.uni4life.util.ValueUtil.econdeString;
import static java.util.Objects.isNull;

import java.util.function.BiFunction;
import java.util.function.Function;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraUsuarioRequest;

public class UsuarioEntityMapper implements BiFunction<CadastraUsuarioRequest, String, UsuarioEntity> {

    @Override
    public UsuarioEntity apply(final CadastraUsuarioRequest request, final String arquivoImagem) {
        if (isNull(request)) {
            return null;
        }

        final UsuarioEntity entity = new UsuarioEntity();

        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setSenha(econdeString(request.getSenha()));
        entity.setRegistroAcademico(request.getRegistroAcademico());
        entity.setTelefone(request.getTelefone());
        entity.setDataNascimento(request.getDataNascimento());
        entity.setTipo(request.getTipoConta());
        entity.setSegmento(ACADEMICO);
        entity.setImagem(arquivoImagem);

        return entity;
    }

}
