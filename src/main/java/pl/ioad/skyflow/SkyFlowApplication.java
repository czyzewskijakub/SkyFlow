package pl.ioad.skyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.ioad.skyflow.logic.flight.opensky.Credentials;

import java.util.Locale;

@SpringBootApplication
@EnableConfigurationProperties(Credentials.class)
public class SkyFlowApplication {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
    public static void main(String[] args) {
        SpringApplication.run(SkyFlowApplication.class, args);
    }

}
