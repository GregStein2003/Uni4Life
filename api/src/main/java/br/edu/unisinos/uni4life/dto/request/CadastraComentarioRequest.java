package br.edu.unisinos.uni4life.dto.request;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CadastraComentarioRequest implements Serializable {

    private static final long serialVersionUID = -3795293360906102908L;

    @NotBlank(message = "{titulo-comentario.invalido}")
    @ApiModelProperty(value = "Título do comentário para ser cadastrado", required = true, example = "Meu Título")
    private final String titulo;

    @NotBlank(message = "{descricao-conteudo.invalido}")
    @ApiModelProperty(value = "Descrição do comentário para ser cadastrado", required = true, example = "Essa é a "
        + "descrição do meu comentário")
    private final String descricao;

    @NotNull(message = "{identificador-conteudo.invalido}")
    @ApiModelProperty(value = "Identificador do contéudo para ser comentado", required = true, example = "2d963fbc-c346-4133-a218-ef5e7bcc2071")
    private final UUID idConteudo;

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("titulo", titulo)
            .append("descricao", descricao)
            .toString();
    }
}
