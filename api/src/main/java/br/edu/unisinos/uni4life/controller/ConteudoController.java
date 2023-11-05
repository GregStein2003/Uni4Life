package br.edu.unisinos.uni4life.controller;

import static br.edu.unisinos.uni4life.util.ValueUtil.paginacao;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.request.CadastraConteudoRequest;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;
import br.edu.unisinos.uni4life.service.ConteudoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/conteudos")
@RequiredArgsConstructor
public class ConteudoController implements ConteudoApi {

    private final ConteudoService conteudoService;

    @Override
    @GetMapping("/meu")
    public Pagina<ConteudoResponse> consultar(@RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        return new Pagina<>(conteudoService.consultar(paginacao(pagina, tamanho, Sort.by("dataCriacao"))));
    }

    @Override
    @GetMapping
    public ConteudoResponse consultar(@RequestParam("id") @NotBlank final UUID idConteudo) {
        return conteudoService.consultar(idConteudo);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public ConteudoResponse cadastrar(@RequestBody @Valid final CadastraConteudoRequest request) {
        return conteudoService.cadastrar(request);
    }
}
