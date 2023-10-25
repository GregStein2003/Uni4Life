package br.edu.unisinos.uni4life.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.uni4life.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {

    private final UsuarioService usuarioService;

    @Override
    @GetMapping
    public String consultar(@RequestParam(value = "id") final Long id) {
        return usuarioService.consultar(id);
    }
}
