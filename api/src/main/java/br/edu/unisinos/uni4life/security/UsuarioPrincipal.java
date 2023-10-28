package br.edu.unisinos.uni4life.security;

import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UsuarioPrincipal implements UserDetails {

   private static final long serialVersionUID = -4064490654352652330L;

   private UUID id;
   private String senha;
   private String email;
   private List<SimpleGrantedAuthority> permissoes;

   public UsuarioPrincipal() {
   }

   public UsuarioPrincipal(final UsuarioEntity usuario) {
      this.id = usuario.getId();
      this.senha = usuario.getSenha();
      this.email = usuario.getEmail();
      this.permissoes = singletonList(new SimpleGrantedAuthority(usuario.getSegmento().getRole()));
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.permissoes;
   }

   @Override
   public String getPassword() {
      return this.senha;
   }

   @Override
   public String getUsername() {
      return this.email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return this.isEnabled();
   }

   @Override
   public boolean isAccountNonLocked() {
      return this.isEnabled();
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return this.isEnabled();
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
