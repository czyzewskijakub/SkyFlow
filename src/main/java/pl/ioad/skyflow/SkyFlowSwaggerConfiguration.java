package pl.ioad.skyflow;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
        return new OpenAPI().info(new Info()
                .title("SkyFlow API")
                .version("1.0.0")
                .description("SkyFlow flights reservations system documentation"));
    }

}

