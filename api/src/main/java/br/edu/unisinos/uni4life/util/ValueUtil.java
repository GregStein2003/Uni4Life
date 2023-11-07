package br.edu.unisinos.uni4life.util;

import static java.util.Objects.nonNull;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ValueUtil {

    public static String toHashMD5(final String str) {
        return nonNull(str) ? md5Hex(str) : "";
    }

    public static String econdeString(final String str) {
        return nonNull(str) ? new BCryptPasswordEncoder().encode(str) : "";
    }

    public static Pageable paginacao(final Integer pagina, final Integer tamanho, final Sort ordenacao) {
        final int numeroPagina = Optional.ofNullable(pagina)
            .filter(page -> page > 0)
            .map(page -> page - 1)
            .orElse(0);
        final int numeroElementos =  Optional.ofNullable(tamanho).orElse(10);
        return PageRequest.of(numeroPagina, numeroElementos, ordenacao);
    }
}
