package br.edu.unisinos.uni4life.dto.response;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import br.edu.unisinos.uni4life.domain.enumeration.conteudo.TipoConteudo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ConteudoResponse implements Serializable {

    private static final long serialVersionUID = -2581146118352825343L;

    @ApiModelProperty(value = "Identificador do conteúdo", example = "2d963fbc-c346-4133-a218-ef5e7bcc2071")
    private final UUID id;

    @ApiModelProperty(value = "Título do conteúdo", example = "Meu Título")
    private final String titulo;

    @ApiModelProperty(value = "Descrição do conteúdo", example = "Essa é a "
        + "descrição do meu conteúdo")
    private final String descricao;

    @ApiModelProperty(value = "Tipo do conteúdo", example = "LIVRO")
    private final TipoConteudo tipoConteudo;

    @ApiModelProperty(value = "Link HTTP do conteúdo")
    private final String link;

    @ApiModelProperty(value = "Flag booleana indicando se conteudo foi favoritado.")
    private Boolean favoritado;

    @ApiModelProperty(value = "Flag booleana indicando se conteudo foi curtido.")
    private Boolean curtido;

    @ApiModelProperty(value = "Identificador do autor do conteúdo", example = "2d963fbc-c346-4133-a218-ef5e7bcc2072")
    private final UUID idAutor;

    @ApiModelProperty(value = "Nome do autor do conteúdo", example = "Fulano da Silva")
    private final String autor;

    @ApiModelProperty(value = "Imagem do autor do conteúdo")
    private final String imagemAutor;

    @ApiModelProperty(value = "Data de criação do conteúdo", example = "2023-05-23")
    private final LocalDate dataCriacao;

    @ApiModelProperty(value = "Data de atualização do conteúdo", example = "2023-06-21")
    private final LocalDate dataAtualizacao;

    @ApiModelProperty(value = "Imagem do conteúdo em Base64")
    private final String imagem;

    @ApiModelProperty(value = "Comentários do conteúdo")
    private final List<ComentarioResponse> comentarios;

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("titulo", titulo)
            .append("descricao", descricao)
            .append("tipoConteudo", tipoConteudo)
            .append("link", link)
            .append("favoritado", favoritado)
            .append("curtido", curtido)
            .append("autor", autor)
            .append("dataCriacao", dataCriacao)
            .append("dataAtualizacao", dataAtualizacao)
            .toString();
    }

}
