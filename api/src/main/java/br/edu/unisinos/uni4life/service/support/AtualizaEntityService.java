package br.edu.unisinos.uni4life.service.support;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.DATA_NASCIMENTO_INVALIDA;
import static br.edu.unisinos.uni4life.domain.enumeration.Message.IMAGEM_INVALIDA;
import static java.util.Optional.ofNullable;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.dto.request.AtualizaUsuarioRequest;
import br.edu.unisinos.uni4life.exception.ClientErrorException;
import br.edu.unisinos.uni4life.validator.EmailValidator;
import br.edu.unisinos.uni4life.validator.TelefoneValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizaEntityService {

    private final EmailValidator emailValidator;
    private final TelefoneValidator telefoneValidator;
    private final MessageService messageService;
    private final StorageService storageService;
    private final PasswordEncoder encoder;

    public UsuarioEntity atualizar(final AtualizaUsuarioRequest request, final UsuarioEntity entity) {
        ofNullable(request.getNome())
            .filter(StringUtils::isNotBlank)
            .ifPresent(entity::setNome);

        ofNullable(request.getEmail())
            .filter(StringUtils::isNotBlank)
            .ifPresent(email -> {
                emailValidator.accept(email);
                entity.setEmail(email);
            });

        ofNullable(request.getSenha())
            .filter(StringUtils::isNotBlank)
            .ifPresent(senha -> entity.setSenha(encoder.encode(senha)));

        ofNullable(request.getRegistroAcademico())
            .filter(StringUtils::isNotBlank)
            .ifPresent(entity::setRegistroAcademico);

        ofNullable(request.getTelefone())
            .filter(StringUtils::isNotBlank)
            .ifPresent(telefone -> {
                telefoneValidator.accept(telefone);
                entity.setTelefone(telefone);
            });

        ofNullable(request.getDataNascimento())
            .ifPresent(data -> {
                if (LocalDate.now().isBefore(data)) {
                    log.warn("Data de nascimento inválida ou não informada");
                    throw new ClientErrorException(VALIDATION, messageService.get(DATA_NASCIMENTO_INVALIDA),
                        "dataNascimento");
                }

                entity.setDataNascimento(data);
            });

        ofNullable(request.getTipoConta())
            .ifPresent(entity::setTipo);

        ofNullable(request.getImagem())
            .ifPresent(imagemBase64 -> {
                final String arquivo = salvarImagem(imagemBase64);
                entity.setImagem(arquivo);
            });

        return entity;
    }

    private String salvarImagem(final String imagemBase64) {
        try {
            return storageService.salvar(imagemBase64);
        } catch (final Exception exception) {
            log.debug("Erro para salvar imagem: {}", exception.getMessage());
            throw new ClientErrorException(VALIDATION, messageService.get(IMAGEM_INVALIDA), "imagem");
        }
    }
}
