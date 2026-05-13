package com.maks.subscriptionsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApiConfig() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> openApi.setTags(List.of(
                new Tag().name("Authentication").description("Authentication management APIs"),
                new Tag().name("Subscriptions").description("Subscription management APIs"),
                new Tag().name("Users").description("User management APIs"),
                new Tag().name("Plans").description("Plan management APIs"),
                new Tag().name("Invoices").description("Invoice management APIs"),
                new Tag().name("Payments").description("Payment management APIs")
        ));
    }
}
