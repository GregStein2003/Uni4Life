package br.edu.unisinos.uni4life.dto.request;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;

import br.edu.unisinos.uni4life.domain.enumeration.conteudo.TipoConteudo;
import br.edu.unisinos.uni4life.validator.TipoConteudoValido;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CadastraConteudoRequest implements Serializable {

    private static final long serialVersionUID = 8124249780349737828L;

    @NotBlank(message = "{titulo-conteudo.invalido}")
    @ApiModelProperty(value = "Título do conteúdo para ser cadastrado", required = true, example = "Meu Título")
    private final String titulo;

    @NotBlank(message = "{descricao-conteudo.invalido}")
    @ApiModelProperty(value = "Descrição do conteúdo para ser cadastrado", required = true, example = "Essa é a "
        + "descrição do meu conteúdo")
    private final String descricao;

    @TipoConteudoValido(anyOf = {TipoConteudo.LIVRO, TipoConteudo.VIDEO, TipoConteudo.PODCAST,
        TipoConteudo.MUSICA, TipoConteudo.TEXTO})
    @ApiModelProperty(value = "Tipo do conteúdo para ser cadastrado", required = true, example = "LIVRO",
        allowableValues = "LIVRO, VIDEO, PODCAST, MUSICA, TEXTO")
    private final TipoConteudo tipoConteudo;

    @ApiModelProperty(value = "Link HTTP do conteúdo para ser cadastrado")
    private final String link;

    @ApiModelProperty(value = "Imagem do conteúdo em Base64.")
    private String imagem;

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("titulo", titulo)
            .append("descricao", descricao)
            .append("tipoConteudo", tipoConteudo)
            .append("link", link)
            .toString();
    }
}
