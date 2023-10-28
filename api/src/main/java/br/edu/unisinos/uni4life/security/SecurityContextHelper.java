package br.edu.unisinos.uni4life.security;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

public final class SecurityContextHelper {

   /**
    * Retorna usuário autenticado na aplicação
    */
   public static UsuarioPrincipal getUsuarioPrincipal() {
      return (UsuarioPrincipal) SecurityContextHolder.getContext().getAuthentication()
          .getPrincipal();
   }
}
