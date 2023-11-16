package br.edu.unisinos.uni4life.controller;

import static br.edu.unisinos.uni4life.util.ValueUtil.paginacao;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.request.CadastraUsuarioRequest;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import br.edu.unisinos.uni4life.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {

    private final UsuarioService usuarioService;

    @Override
    @GetMapping
    public UsuarioResponse consultar(@RequestParam(value = "id", required = false) @NotBlank final UUID idUsuario) {
        return usuarioService.consultar(idUsuario);
    }

    @Override
    @GetMapping("/seguidos")
    public Pagina<UsuarioResponse> consultarUsuariosSeguidos(
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho, Sort.by("dataInicioRelacionamento"));
        return new Pagina<>(usuarioService.consultarUsuariosSeguidos(paginacao));
    }

    @Override
    @GetMapping("/seguir")
    public Pagina<UsuarioResponse> consultarUsuariosParaSeguir(
        @RequestParam(value = "pagina", required = false) final Integer pagina,
        @RequestParam(value = "tamanho", required = false) final Integer tamanho) {
        final Pageable paginacao = paginacao(pagina, tamanho);
        return new Pagina<>(usuarioService.consultarUsuariosParaSeguir(paginacao));
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioResponse cadastrar(@RequestBody @Valid final CadastraUsuarioRequest request) {
        return usuarioService.cadastrar(request);
    }
}
