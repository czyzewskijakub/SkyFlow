package pl.ioad.skyflow;


import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ioad.skyflow.logic.flight.opensky.Credentials;

@OpenAPIDefinition
@Configuration
@EnableConfigurationProperties(Credentials.class)
public class SkyFlowSwaggerConfiguration {
    @Bean
    public OpenAPI baseOpenApi() {
        String authName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(authName))
                .components(
                        new Components()
                                .addSecuritySchemes(authName,
                                        new SecurityScheme()
                                                .name(authName)
                                                .type(HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT"))
                )
                .info(new Info()
                    .title("SkyFlow API")
                    .version("1.0.0")
                    .description("SkyFlow flights reservations system documentation")
                );
    }
}

