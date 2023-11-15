package br.edu.unisinos.uni4life.service.support;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    @Value("${storage.path}")
    private String storagePath;

    private final MessageService messageService;

    public String salvar(final String arquivoBase64) {
        if (isBlank(arquivoBase64)) {
            return null;
        }

        final byte[] arquivoBytes = Base64.getDecoder().decode(arquivoBase64);
        final String nomeArquivo = UUID.nameUUIDFromBytes(arquivoBytes)
            .toString();

        log.debug("Salvando arquivo com nome: {}", nomeArquivo);

        final Path path = Paths.get(format("%s%s", storagePath, nomeArquivo));

        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
                Files.write(path, arquivoBytes);
            }
        } catch (final IOException exception) {
            log.warn("Não foi possível salvar a imagem do usuário: {}", exception.getMessage(), exception);
            return null;
        }

        return nomeArquivo;
    }

    public String consultar(final String nomeArquivo) {
        log.debug("Consultando arquivo: {}", nomeArquivo);

        if (isBlank(nomeArquivo)) {
            return null;
        }

        final Path path = Paths.get(String.format("%s%s", storagePath, nomeArquivo));
        if (Files.notExists(path)) {
            return null;
        }
        try {
            final byte[] arquivoBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(arquivoBytes);
        } catch (final IOException exception) {
            log.warn("Não foi possível consultar imagem do usuário: {}", exception.getMessage(), exception);
            return null;
        }
    }

}
