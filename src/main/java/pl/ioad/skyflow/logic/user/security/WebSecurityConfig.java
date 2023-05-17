package pl.ioad.skyflow.logic.user.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.ioad.skyflow.logic.user.security.jwt.AuthEntryPointJwt;
import pl.ioad.skyflow.logic.user.security.jwt.AuthTokenFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[][] AUTHORIZED_ONLY = {
            {"/flights/find"}
    };
    private static final String[][] SWAGGER_WHITELIST = {
            {"/**/swagger-ui/**", "/**/swagger-resources/**",
                    "/**/swagger-resources/**", "/**/swagger-ui.html/**",
                    "/**/webjars/**", "/**/swagger-ui/**"},
            { "/**/swagger-ui/index.html/**",
                    "/**/v2/api-docs/**", "/**/configuration/ui/**",
                    "/**/configuration/security/**", "/**/v3/api-docs/**"}
    };

    @Bean
    public AuthEntryPointJwt getUnauthorizedHandler() {
        return new AuthEntryPointJwt();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(getUnauthorizedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                    .requestMatchers(AUTHORIZED_ONLY[0][0]).hasRole("USER")
                    .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}