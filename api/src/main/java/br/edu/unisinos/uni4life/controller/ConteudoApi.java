package br.edu.unisinos.uni4life.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
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
    Pagina<ConteudoResponse> consultar(@RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável por consultar todos os conteúdos de um usuário.",
        notes = "Operação consulta os conteúdos cadastrados pelo usuário do id informado "
            + "na base de dados. Caso não econtre nenhum conteúdo retorna uma lista vazia. "
            + "Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ConteudoResponse> consultar(@RequestParam(value = "idUsuario") final UUID idUsuario,
        @RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável por consultar todos os conteúdos seguidos.",
        notes = "Operação consulta os conteúdos de todos os usuários seguidos pelo usuário autenticado"
            + " na base de dados. Os contéudos retornados são apenas daqueles usuários que possuem"
            + " a conta do tipo <strong>PUBLICA</strong>"
            + " Caso não econtre nenhum conteúdo retorna uma lista vazia. "
            + "Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ConteudoResponse> consultarConteudosSeguidos(
        @RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável por consultar todos os conteúdos favoritados.",
        notes = "Operação consulta os conteúdos favoritados pelo usuário autenticado"
            + " na base de dados. Caso não econtre nenhum conteúdo retorna uma lista vazia."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ConteudoResponse> consultarConteudosFavoritados(
        @RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável por consultar todos os conteúdos curtidos.",
        notes = "Operação consulta os conteúdos curtidos pelo usuário autenticado"
            + " na base de dados. Caso não econtre nenhum conteúdo retorna uma lista vazia."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    Pagina<ConteudoResponse> consultarConteudosCurtidos(
        @RequestParam(value = "pagina", required = false) Integer pagina,
        @RequestParam(value = "tamanho", required = false) Integer tamanho);

    @ApiOperation(value = "Operação responsável em consultar informações de um conteúdo.",
        notes = "Operação consulta os dados do conteúdo pelo  identificador informado "
            + "na base de dados. Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 500, message = "Erro Interno.")
    })
    ConteudoResponse consultar(@PathVariable("id") final UUID idConteudo);

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

    @ApiOperation(value = "Operação responsável por favoritar um conteúdo da plataforma.",
        notes = "Operação recebe o id do conteúdo para ser favoritado pelo usuário autenticado."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 400, message = "<ul>\t\n"
            + "<li>Conteúdo já favoritado.</li>\t\n"
            + "</ul>"),
        @ApiResponse(code = 404, message = "Conteúdo não encontrado."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ConteudoResponse favoritar(@RequestParam final UUID idConteudo);

    @ApiOperation(value = "Operação responsável por desfavoritar um conteúdo da plataforma.",
        notes = "Operação recebe o id do conteúdo para ser desfavoritado pelo usuário autenticado."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Conteúdo não encontrado."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ConteudoResponse desfavoritar(@RequestParam final UUID idConteudo);

    @ApiOperation(value = "Operação responsável por curtir um conteúdo da plataforma.",
        notes = "Operação recebe o id do conteúdo para ser curtido pelo usuário autenticado."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 400, message = "<ul>\t\n"
            + "<li>Conteúdo já curtido.</li>\t\n"
            + "</ul>"),
        @ApiResponse(code = 404, message = "Conteúdo não encontrado."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ConteudoResponse curtir(@RequestParam final UUID idConteudo);

    @ApiOperation(value = "Operação responsável por descurtir um conteúdo da plataforma.",
        notes = "Operação recebe o id do conteúdo para ser descurtido pelo usuário autenticado."
            + " Esse endpoint é necessário estar <strong>autenticado</strong>")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso."),
        @ApiResponse(code = 401, message = "Usuário não autenticado."),
        @ApiResponse(code = 404, message = "Conteúdo não encontrado."),
        @ApiResponse(code = 500, message = "Erro interno.")
    })
    ConteudoResponse descurtir(@RequestParam final UUID idConteudo);

}
