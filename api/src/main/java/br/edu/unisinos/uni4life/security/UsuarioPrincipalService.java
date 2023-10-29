package br.edu.unisinos.uni4life.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import br.edu.unisinos.uni4life.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioPrincipalService implements UserDetailsService {

   private final UsuarioRepository repository;

   @Override
   public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
      log.info("Realizando login para: {}", email);

      final UsuarioEntity usuario = repository.findByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException(email));

      return new UsuarioPrincipal(usuario);
   }
}
