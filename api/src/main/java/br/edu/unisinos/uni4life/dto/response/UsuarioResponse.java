package br.edu.unisinos.uni4life.dto.response;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.edu.unisinos.uni4life.domain.enumeration.usuario.Segmento;
import br.edu.unisinos.uni4life.domain.enumeration.usuario.TipoConta;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UsuarioResponse implements Serializable {

    private static final long serialVersionUID = -1563990562794161133L;

    @ApiModelProperty(value = "Identificador do usuário cadastrado", example = "2d963fbc-c346-4133-a218-ef5e7bcc2071")
    private final UUID id;

    @ApiModelProperty(value = "Nome do usuário cadastrado", example = "Fulano Silva")
    private final String nome;

    @ApiModelProperty(value = "Email do usuário cadastrado", example = "fulano@example.com")
    private final String email;

    @ApiModelProperty(value = "Registro acâdemico do usuário cadastrado", example = "91601504")
    private final String registroAcademico;

    @ApiModelProperty(value = "Telefone do usuário cadastrado", example = "(51)99999-9999")
    private final String telefone;

    @ApiModelProperty(value = "Data nascimento do usuário cadastrado", example = "yyyy-MM-dd")
    private final LocalDate dataNascimento;

    @ApiModelProperty(value = "Tipo da conta do usuário cadastrado", example = "PUBLICA")
    private final TipoConta tipoConta;

    @ApiModelProperty(value = "Tipo de segmento do usuário", example = "ACADEMICO", allowableValues = "ACADEMICO, "
        + "ADMINISTRADOR")
    private final Segmento segmento;

    @ApiModelProperty(value = "Quantidade de seguidores na plataforma", example = "5")
    private final Long seguidores;

    @JsonInclude(value = Include.NON_NULL)
    @ApiModelProperty(value = "Data de inicio do relacionamento com usuário", example = "2023-09-11")
    private LocalDate dataRelacionamento;

    @JsonInclude(value = Include.NON_NULL)
    @ApiModelProperty(value = "Flag booleana caso o usuário consultado é seguido pelo usuário autenticado",
        example = "true", allowableValues = "true, false")
    private Boolean seguido;

    @ApiModelProperty(value = "Imagem do usuário em Base64")
    private final String imagem;

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("nome", nome)
            .append("email", email)
            .append("registroAcademico", registroAcademico)
            .append("telefone", telefone)
            .append("dataNascimento", dataNascimento)
            .append("tipoConta", tipoConta)
            .append("segmento", segmento)
            .append("seguidores", seguidores)
            .toString();
    }

}
