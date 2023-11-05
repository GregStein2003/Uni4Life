package br.edu.unisinos.uni4life.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.request.CadastraConteudoRequest;
import br.edu.unisinos.uni4life.dto.response.ConteudoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "ConteudoApi")
public interface ConteudoApi {

    @ApiOperation(value = "Operação responsável por consultar todos os conteúdos do usuário autenticado.",
        notes = "Operação consulta os conteúdos cadastrados pelo usuário "
            + "na base de dados. Caso não econtre nenhum conteúdo retorna uma lista vazia. "
            + "Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ConteudoResponse> consultar(@RequestParam("pagina") Integer pagina,
        @RequestParam("tamanho") Integer tamanho);

    @ApiOperation(value = "Operação responsável em consultar informações de um conteúdo.",
        notes = "Operação consulta os dados do conteúdo pelo  identificador informado "
            + "na base de dados. Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Nenhum conteúdo não encontrado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    ConteudoResponse consultar(@RequestParam("id") final UUID idConteudo);

    @ApiOperation(value = "Operação responsável por cadastrar novo conteúdo.",
        notes = "Operação valida campos da requisição e após isso" +
            " cria novo registro de conteúdo na base de dados, vinculado ao usuário autenticado."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Criado com sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 400, message = "Requisição inválida."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ConteudoResponse cadastrar(@RequestBody final CadastraConteudoRequest request);

}
