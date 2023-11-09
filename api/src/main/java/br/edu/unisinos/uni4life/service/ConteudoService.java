package br.edu.unisinos.uni4life.service;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.NOT_FOUND;
import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.CONTEUDO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.LINK_CONTEUDO_INVALIDO;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.USUARIO_NAO_ENCONTRADO;
import static br.edu.unisinos.uni4life.security.SecurityContextHelper.getUsuarioPrincipalId;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.ConteudoEnitity;
import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.CadastraConteudoRequest;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.mapper.ConteudoEntityMapper;
import br.edu.unisinos.uni4life.mapper.ConteudoResponseMapper;
import br.edu.unisinos.uni4life.repository.ConteudoRepository;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import br.edu.unisinos.uni4life.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConteudoService {

    private static final ConteudoEntityMapper CONTEUDO_MAPPER = new ConteudoEntityMapper();

    private final UsuarioRepository userRepository;
    private final ConteudoRepository repository;
    private final MessageService messageService;

    public ConteudoResponse consultar(final UUID idConteudo) {
        log.info("Consultando conteúdo com ID: {}", idConteudo);

        return repository.findById(idConteudo)
            .map(new ConteudoResponseMapper())
            .orElseThrow(() -> {
                log.warn("Conteúdo não encontrado com ID: {}", idConteudo);
                return new ClientErrorException(NOT_FOUND, messageService.get(CONTEUDO_NAO_ENCONTRADO));
            });
    }

    public Page<ConteudoResponse> consultar(final Pageable paginacao) {
        final UUID idAutor = getUsuarioPrincipalId();
        log.info("Consultando conteúdo do usuário: {}", idAutor);

        return repository.findByAutorId(idAutor, paginacao)
            .map(new ConteudoResponseMapper());

    }

    public Page<ConteudoResponse> consultarConteudosSeguidos(final Pageable paginacao) {
        log.info("Consultando conteúdos seguidos pelo usuário: {}", getUsuarioPrincipalId());

        return repository.findConteudoSeguido(getUsuarioPrincipalId(), paginacao)
            .map(new ConteudoResponseMapper());
    }

    @Transactional
    public ConteudoResponse cadastrar(final CadastraConteudoRequest request) {
        log.info("Realizando cadastro do conteudo: {}", request);

        final UsuarioEntity autor = getAutor(getUsuarioPrincipalId());

        if (validaLinkConteudo(request)) {
            log.warn("Link não informado ou inválido.");
            throw new ClientErrorException(VALIDATION, messageService.get(LINK_CONTEUDO_INVALIDO), "link");
        }

        final ConteudoEnitity enitity = repository.save(CONTEUDO_MAPPER.apply(request, autor));

        return new ConteudoResponseMapper().apply(enitity);
    }

    private UsuarioEntity getAutor(final UUID idAutor) {
        return userRepository.findById(idAutor)
            .orElseThrow(() -> {
                log.warn("Autor não encontrado");
                return new ClientErrorException(NOT_FOUND, messageService.get(USUARIO_NAO_ENCONTRADO));
            });
    }

    private boolean validaLinkConteudo(final CadastraConteudoRequest request) {
        return request.getTipoConteudo().isPrecisaLink() && isBlank(request.getLink());
    }
}
