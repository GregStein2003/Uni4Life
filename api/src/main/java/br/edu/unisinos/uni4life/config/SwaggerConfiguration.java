package br.edu.unisinos.uni4life.config;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.ArrayUtils.nullToEmpty;
import static org.apache.commons.lang3.ArrayUtils.removeElements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {

    @Value("${server.port}")
    private String serverPort;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    private static final String TOKEN_URL = "http://localhost:%s/uni4life/oauth/token";

    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_NAME = "name";

    private final ApiDefinition apiDefinition;

    @Bean
    public Docket api(
        @Value("${springfox.documentation.info.title}") final String title,
        @Value("${springfox.documentation.info.version}") final String version,
        @Value("${springfox.documentation.info.description}") final String description,
        @Value("${springfox.documentation.base-package}") final String basePackage) {

        final Tag[] tags = criaTags();
        final Tag firstTag = tags[0];

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo(title, description, version))
            .securityContexts(singletonList(securityContext()))
            .securitySchemes(singletonList(securitySchema()))
            .directModelSubstitute(LocalDate.class, String.class)
            .useDefaultResponseMessages(false)
            .tags(firstTag, nullToEmpty(removeElements(tags, firstTag), Tag[].class))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .build();
    }

    private ApiInfo apiInfo(final String title, final String description, final String version) {

        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
            .build();
    }

    private Tag[] criaTags() {
        final Set<Tag> tagSet = apiDefinition.getApis()
            .stream()
            .filter(d -> d.containsKey(TAG_NAME))
            .filter(d -> d.containsKey(TAG_DESCRIPTION))
            .map(d -> new Tag(d.get(TAG_NAME), d.get(TAG_DESCRIPTION)))
            .collect(Collectors.toSet());

        final Tag[] tags = new Tag[tagSet.size()];
        tagSet.toArray(tags);
        return tags;
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
            .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("any", "do all");


        return singletonList(new SecurityReference("Oauth2schema", authorizationScopes));
    }

    private OAuth securitySchema() {
        final List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("any", "do all"));;

        final List<GrantType> grantTypes = new ArrayList();
        final GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(format(TOKEN_URL, serverPort));

        grantTypes.add(creGrant);

        return new OAuth("Oauth2schema", authorizationScopeList, grantTypes);
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties("springfox.documentation")
    private static final class ApiDefinition {

        private List<Map<String, String>> apis;
    }

}
