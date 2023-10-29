package br.edu.unisinos.uni4life.security.oauth;

import static br.edu.unisinos.uni4life.domain.enumeration.ErrorType.VALIDATION;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import br.edu.unisinos.uni4life.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.accessTokenValidititySeconds}")
    private Integer accessTokenValiditySeconds;

    @Value("${security.refreshTokenValiditySeconds}")
    private Integer refreshTokenValiditySeconds;

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(clientId)
            .secret(passwordEncoder.encode(clientSecret))
            .accessTokenValiditySeconds(accessTokenValiditySeconds)
            .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
            .scopes("any", "app")
            .resourceIds("uni4life-api");
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .userDetailsService(userDetailsService)
            .authenticationManager(authenticationManager)
            .exceptionTranslator(webResponseExceptionTranslator());
    }

    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return exception -> {
            final ErrorResponse error = ErrorResponse.builder()
                .errorType(VALIDATION)
                .message("Credenciais inválidas, verifique os dados informados.")
                .details(emptyMap())
                .build();

            return new ResponseEntity<>(error, BAD_REQUEST);
        };
    }
}
