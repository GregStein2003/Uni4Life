package br.edu.unisinos.uni4life.util;

import static java.util.Objects.nonNull;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ValueUtil {

    public static String toHashMD5(final String str) {
        return nonNull(str) ? md5Hex(str) : "";
    }

    public static String econdeString(final String str) {
        return nonNull(str) ? new BCryptPasswordEncoder().encode(str) : "";
    }
}
