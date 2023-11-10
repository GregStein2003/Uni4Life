package br.edu.unisinos.uni4life.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "SeguidoresApi")
public interface SeguidorApi {

    @ApiOperation(value = "Operação por consulta os seguidores do usuário.",
        notes = "Operação consulta todos os seguidores do usuário autenticado na base de dados."
            + " Caso ele não tenha nenhum usuário como seguidor é retornado então uma lista vazia."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<UsuarioResponse> consultar(@RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável seguir um usuário na plataforma.",
        notes = "Operação recebe o id do usuário a ser seguido, e salva o relacionamento"
            + " com o usuário autenticado. Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Sucesso."),
        @ApiResponse(code = 400, message = "<ul>\t\n"
            + "<li>Não é permitido seguir a você mesmo.</li>\t\n"
            + "<li>Usuário já esta sendo seguido.</li>\t\n"
            + "</ul>"),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Usuário não encontrado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    void seguir(@RequestParam("idSeguido") final UUID idSeguido);

    @ApiOperation(value = "Operação responsável remover relação entres um usuários na plataforma.",
        notes = "Operação recebe o id do usuário seguido, remove o relacionamento entre ele"
            + " com o usuário autenticado. Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Usuário não encontrado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    void remover(@RequestParam("idSeguido") final UUID idSeguido);

}
