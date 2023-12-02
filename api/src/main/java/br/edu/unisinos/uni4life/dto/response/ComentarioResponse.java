package br.edu.unisinos.uni4life.dto.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ComentarioResponse implements Serializable {

    private static final long serialVersionUID = 8093403832469592923L;

    @ApiModelProperty(value = "Identificador do comentário", example = "2d963fbc-c346-4133-a218-ef5e7bcc2071")
    private final UUID id;

    @ApiModelProperty(value = "Título do comentário", example = "Meu Título")
    private final String titulo;

    @ApiModelProperty(value = "Descrição do comentário", example = "Essa é a "
        + "descrição do meu comentário")
    private final String descricao;

    @ApiModelProperty(value = "Data de criação do comentário", example = "2023-05-23")
    private final LocalDate dataCriacao;

    @ApiModelProperty(value = "Data de atualização do comentário", example = "2023-06-21")
    private final LocalDate dataAtualizacao;

    @ApiModelProperty(value = "Nome do autor do comentário", example = "Fulano da Silva")
    private final String autor;

    @ApiModelProperty(value = "Imagem do autor do comentário")
    private final String imagemAutor;

    @JsonInclude(value = Include.NON_NULL)
    @ApiModelProperty(value = "Título do conteúdo comentado", example = "Titulo comentado")
    private final String tituloConteudo;

}
