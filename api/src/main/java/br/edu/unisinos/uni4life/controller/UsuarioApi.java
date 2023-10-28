package br.edu.unisinos.uni4life.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unisinos.uni4life.dto.request.CadastraUsuarioRequest;
import br.edu.unisinos.uni4life.dto.response.UsuarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "UsuarioApi")
public interface UsuarioApi {

    @ApiOperation(value = "Operação responsável em consultar informações de um usuário.",
        notes = "Operação consulta os dados do usuário do identificador informado, "
            + "na base de dados. Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Usuário não encontrado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    UsuarioResponse consultar(@RequestParam("id") final UUID idUsuario);

    @ApiOperation(value = "Operação responsável por cadastrar novo usuário.",
        notes = "Operação valida campos da requisição e após isso" +
            " cria novo registro na base de dados")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Criado com sucesso."),
        @ApiResponse(code = 400, message = "Requisição inválida."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    UsuarioResponse cadastrar(@RequestBody final CadastraUsuarioRequest request);

}
