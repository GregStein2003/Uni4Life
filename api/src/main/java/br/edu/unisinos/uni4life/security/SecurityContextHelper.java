package br.edu.unisinos.uni4life.security;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityContextHelper {

    /**
     * Retorna usuário autenticado na aplicação
     */
    public static UsuarioPrincipal getUsuarioPrincipal() {
        return (UsuarioPrincipal) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
    }

   /**
    * Retorna ID do usuário autenticado na aplicação
    */
    public static UUID getUsuarioPrincipalId() {
        final UsuarioPrincipal usuario = (UsuarioPrincipal) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        return usuario.getId();
    }
}
