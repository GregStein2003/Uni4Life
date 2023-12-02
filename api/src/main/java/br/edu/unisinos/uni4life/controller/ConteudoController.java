package br.edu.unisinos.uni4life.controller;

import static br.edu.unisinos.uni4life.util.ValueUtil.paginacao;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "dataCriacao"));
        return new Pagina<>(conteudoService.consultar(paginacao));
    }

    @Override
    @GetMapping
    public Pagina<ConteudoResponse> consultar(@RequestParam(value = "idUsuario") final UUID idUsuario,
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "dataCriacao"));
        return new Pagina<>(conteudoService.consultar(idUsuario, paginacao));
    }

    @Override
    @GetMapping("/seguidos")
    public Pagina<ConteudoResponse> consultarConteudosSeguidos(
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "dataCriacao"));
        return new Pagina<>(conteudoService.consultarConteudosSeguidos(paginacao));
    }

    @Override
    @GetMapping("/favoritados")
    public Pagina<ConteudoResponse> consultarConteudosFavoritados(
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "F.dataCriacao"));
        return new Pagina<>(conteudoService.consultarConteudosFavoritados(paginacao));
    }

    @Override
    @GetMapping("/curtidos")
    public Pagina<ConteudoResponse> consultarConteudosCurtidos(
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by(Direction.DESC, "CR.dataCriacao"));
        return new Pagina<>(conteudoService.consultarConteudosCurtidos(paginacao));
    }

    @Override
    @GetMapping("/{idConteudo}")
    public ConteudoResponse consultar(@PathVariable @NotBlank final UUID idConteudo) {
        return conteudoService.consultar(idConteudo);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public ConteudoResponse cadastrar(@RequestBody @Valid final CadastraConteudoRequest request) {
        return conteudoService.cadastrar(request);
    }

    @Override
    @PostMapping("/favoritar")
    public ConteudoResponse favoritar(@RequestParam("idConteudo") final UUID idConteudo) {
        return conteudoService.favoritar(idConteudo);
    }

    @Override
    @DeleteMapping("/favoritar")
    public ConteudoResponse desfavoritar(@RequestParam("idConteudo") final UUID idConteudo) {
        return conteudoService.desfavoritar(idConteudo);
    }

    @Override
    @PostMapping("/curtir")
    public ConteudoResponse curtir(@RequestParam("idConteudo") final UUID idConteudo) {
        return conteudoService.curtir(idConteudo);
    }

    @Override
    @DeleteMapping("/curtir")
    public ConteudoResponse descurtir(@RequestParam("idConteudo") final UUID idConteudo) {
        return conteudoService.descurtir(idConteudo);
    }
}
