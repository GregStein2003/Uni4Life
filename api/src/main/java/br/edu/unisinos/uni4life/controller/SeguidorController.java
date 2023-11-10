package br.edu.unisinos.uni4life.controller;

import static br.edu.unisinos.uni4life.util.ValueUtil.paginacao;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import br.edu.unisinos.uni4life.service.SeguidoresService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/seguidores")
@RequiredArgsConstructor
public class SeguidorController implements SeguidorApi {

    private final SeguidoresService seguidoresService;

    @Override
    @GetMapping("/meu")
    public Pagina<UsuarioResponse> consultar(@RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        return new Pagina<>(seguidoresService.consultarSeguidores(paginacao(pagina, tamanho,
            Sort.by("dataInicioRelacionamento"))));
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public void seguir(@RequestParam("idSeguido") final UUID idSeguido) {
        seguidoresService.seguir(idSeguido);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void remover(@RequestParam("idSeguido") final UUID idSeguido) {
        seguidoresService.removerSeguidor(idSeguido);
    }
}
