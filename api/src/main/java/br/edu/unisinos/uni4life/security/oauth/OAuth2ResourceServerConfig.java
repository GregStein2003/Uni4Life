package br.edu.unisinos.uni4life.security.oauth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) throws Exception {
        config.resourceId("uni4life-api");
    }

    // Configuração das rotas
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().sameOrigin()
            .and()
            .cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers( "/banco/**").permitAll()
            .antMatchers( "/swagger-ui.html/**").permitAll()
            .antMatchers( "/swagger-resources/**").permitAll()
            .antMatchers( "/webjars/**").permitAll()
            .antMatchers( "/v2/api-docs/**").permitAll()
            .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint);
    }
}
