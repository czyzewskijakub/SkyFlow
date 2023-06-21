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

    private final String[][] adminOnly = {
            {"/users/register/admin", "/tickets/classes/{ticketId}"},
            {"/users/all", "/users/type", "/upcomingFlights/add", "/upcomingFlights/clear",
                    "/flights/find", "/tickets/users"}
    };

    private final String[] swaggerWhitelist = {
           "/v3/api-docs.yaml", "/v3/api-docs/**","/swagger-ui/**", "/swagger-ui.html"
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
                    .requestMatchers(adminOnly[0]).hasRole("ADMIN")
                    .requestMatchers(swaggerWhitelist).permitAll()
                    .requestMatchers(adminOnly[1]).hasRole("ADMIN")
                    .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}