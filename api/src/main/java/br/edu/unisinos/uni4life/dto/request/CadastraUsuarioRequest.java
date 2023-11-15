package br.edu.unisinos.uni4life.dto.request;

import static br.edu.unisinos.uni4life.domain.Padroes.TELEFONE_PADRAO;
import static br.edu.unisinos.uni4life.util.ValueUtil.toHashMD5;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.edu.unisinos.uni4life.domain.enumeration.usuario.TipoConta;
import br.edu.unisinos.uni4life.mapper.LocalDateDeserializer;
import br.edu.unisinos.uni4life.validator.TipoContaValido;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CadastraUsuarioRequest implements Serializable {

    private static final long serialVersionUID = -6055899870101044423L;

    @NotBlank(message = "{nome-usuario.invalido}")
    @ApiModelProperty(value = "Nome do usuário para cadastro", required = true, example = "Fulano Silva")
    private final String nome;

    @Email(message = "{email.invalido}")
    @NotBlank(message = "{email.invalido}")
    @ApiModelProperty(value = "Email do usuário para cadastro", required = true, example = "fulano@example.com")
    private final String email;

    @NotBlank(message = "{senha-invalida}")
    @ApiModelProperty(value = "Senha do usuário para login na plataforma", required = true, example = "MinhaSenha123!")
    private final String senha;

    @NotBlank(message = "{registro-academico.invalido}")
    @ApiModelProperty(value = "Registro acadêmico do usuário para cadastro", required = true, example = "91601504")
    private final String registroAcademico;

    @Pattern(regexp = TELEFONE_PADRAO, message = "{telefone.invalido}")
    @NotBlank(message = "{telefone.invalido}")
    @ApiModelProperty(value = "Telefone do usuário, para contato", required = true, example = "(51)99999-9999")
    private final String telefone;

    @PastOrPresent(message = "{data-nascimento.invalida}")
    @NotNull(message = "{data-nascimento.invalida}")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @ApiModelProperty(value = "Data de nascimento do usuário", required = true, example = "yyyy-MM-dd")
    private final LocalDate dataNascimento;

    @TipoContaValido(anyOf = {TipoConta.PUBLICA, TipoConta.PRIVADA})
    @ApiModelProperty(value = "Tipo da conta do usuário", required = true, example = "PUBLICA", allowableValues =
        "PUBLICA, PRIVADA")
    private final TipoConta tipoConta;

    // Campos opcionais:

    @ApiModelProperty(value = "Imagem do usuário codificada em Base64")
    private final String imagem;

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("nome", nome)
            .append("email", email)
            .append("senha", toHashMD5(senha))
            .append("registroAcademico", registroAcademico)
            .append("telefone", telefone)
            .append("dataNascimento", dataNascimento)
            .append("tipoConta", tipoConta)
            .toString();
    }

}
