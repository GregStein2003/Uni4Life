package br.edu.unisinos.uni4life.controller;

import static br.edu.unisinos.uni4life.util.ValueUtil.paginacao;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.request.CadastraComentarioRequest;
import br.edu.unisinos.uni4life.dto.response.ComentarioResponse;
import br.edu.unisinos.uni4life.service.ComentariosService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
public class ComentarioController implements ComentarioApi {

    private final ComentariosService service;

    @Override
    @GetMapping("/meu")
    public Pagina<ComentarioResponse> consultar(
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "dataCriacao"));
        return new Pagina<>(service.consultar(paginacao));
    }

    @Override
    @GetMapping
    public Pagina<ComentarioResponse> consultar(@RequestParam(value = "idConteudo") final UUID idConteudo,
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "dataCriacao"));
        return new Pagina<>(service.consultar(idConteudo, paginacao));
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public ComentarioResponse cadastrar(@RequestBody @Valid final CadastraComentarioRequest request) {
        return service.cadastrar(request);
    }

    @Override
    @DeleteMapping
    public ComentarioResponse remover(@RequestParam(value = "idComentario") final UUID idComentario) {
        return service.remover(idComentario);
    }
}
