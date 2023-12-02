package br.edu.unisinos.uni4life.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unisinos.uni4life.domain.Pagina;
import br.edu.unisinos.uni4life.dto.request.CadastraComentarioRequest;
import br.edu.unisinos.uni4life.dto.response.ComentarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "ComentarioApi")
public interface ComentarioApi {

    @ApiOperation(value = "Operação responsável por consultar todos os comentários do usuário autenticado.",
        notes = "Operação consulta os comentários cadastrados pelo usuário "
            + "na base de dados. Caso não encontre nenhum comentário retorna uma lista vazia. "
            + "Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ComentarioResponse> consultar(@RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável por consultar todos os comentários de um conteúdo.",
        notes = "Operação consulta os comentários cadastrados no conteúdo do id informado "
            + "na base de dados. Caso não encontre nenhum comentário retorna uma lista vazia. "
            + "Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ComentarioResponse> consultar(@RequestParam(value = "idConteudo") final UUID idConteudo,
        @RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);


    @ApiOperation(value = "Operação responsável por cadastrar novo comentário.",
        notes = "Operação valida campos da requisição e após isso" +
            " cria novo registro de comentário na base de dados, vinculado ao usuário autenticado."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Criado com sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Conteúdo não encontrado."),
        @ApiResponse(code = 400, message = "Requisição inválida."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ComentarioResponse cadastrar(@RequestBody final CadastraComentarioRequest request);


    @ApiOperation(value = "Operação responsável por remover um comentário.",
        notes = "Operação valida se comentário para remover é do usuário autenticado" +
            " se for remove o comentário do usuário. Esse endpoint é"
            + " necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Criado com sucesso."),
        @ApiResponse(code = 400, message = "<ul>\t\n"
            + "<li>Requisição inválida.</li>\t\n"
            + "<li>Usuário precisa ser autor do comentário para remover.</li>\t\n"
            + "</ul>"),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Comentário não encontrado."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ComentarioResponse remover(@RequestParam(value = "idComentario") final UUID idComentario);
}
