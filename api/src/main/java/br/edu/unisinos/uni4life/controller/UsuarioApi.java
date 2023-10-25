package br.edu.unisinos.uni4life.controller;

import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unisinos.uni4life.dto.response.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "UsuarioApi")
public interface UsuarioApi {

    // TODO: Remover usado apenas de exemplo;
    @ApiOperation(value = "Operação responsável por consultar usuário.",
        notes = "Operação consulta usuário cadastrado no banco de dados, apartir dos" +
            " parâmetros de entrada.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 400, message = "Parâmetros não inforamados."),
        @ApiResponse(code = 500, message = "Falha interna", response = ErrorResponse.class)
    })
    String consultar(@RequestParam(name = "id") final Long id);
}
