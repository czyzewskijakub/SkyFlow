package pl.ioad.skyflow.logic.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.ioad.skyflow.logic.user.security.jwt.AuthEntryPointJwt;
import pl.ioad.skyflow.logic.user.security.jwt.AuthTokenFilter;

public class WebSecurityConfig {


    @Bean
    private AuthEntryPointJwt getUnauthorizedHandler() {
        return new AuthEntryPointJwt();
    }

    @Bean
    private AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(getUnauthorizedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
