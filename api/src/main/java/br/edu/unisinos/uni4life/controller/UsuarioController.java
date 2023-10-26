package br.edu.unisinos.uni4life.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public UsuarioResponse consultar(@RequestParam("id") @NotBlank final UUID idUsuario) {
        return usuarioService.consultar(idUsuario);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioResponse cadastrar(@RequestBody @Valid final CadastraUsuarioRequest request) {
        return usuarioService.cadastrar(request);
    }
}
