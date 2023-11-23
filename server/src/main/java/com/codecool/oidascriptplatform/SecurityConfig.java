package com.codecool.oidascriptplatform;

import com.codecool.oidascriptplatform.jwt.JwtAuthenticationFilter;
import com.codecool.oidascriptplatform.jwt.JwtEncoder;
import com.codecool.oidascriptplatform.jwt.JwtRememberMeServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    private final JwtAuthenticationFilter authenticationFilter;
//    private final JwtAuthorizationFilter authorizationFilter;

//    public SecurityConfig(
//            JwtAuthenticationFilter authenticationFilter,
//            JwtAuthorizationFilter authorizationFilter,
//            CustomAuthenticationManager authenticationManager
//    ) {
//        this.authenticationFilter = authenticationFilter;
//        this.authorizationFilter = authorizationFilter;
//    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            RememberMeServices rememberMeServices
    ) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/users", "/sessions").permitAll()
                        .anyRequest().authenticated())
//                .addFilter(authenticationFilter)
//                .addFilter(authorizationFilter)
                .authenticationManager(authenticationManager)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices))
                .build();
    }

    @Bean
    public JwtAuthenticationFilter authenticationFilter(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager authCookieManager,
            RememberMeServices rememberMe
    ) {
        RequestMatcher requestMatcher = new AndRequestMatcher(
                new AntPathRequestMatcher("/sessions"),
                request -> HttpMethod.POST.matches(request.getMethod())
        );

        return new JwtAuthenticationFilter(
                requestMatcher,
                authenticationManager,
                jwtEncoder,
                authCookieManager,
                rememberMe
        );
    }

    @Bean
    public JwtRememberMeServices rememberMeServices(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager authCookieManager
    ) {
        return new JwtRememberMeServices(
                authenticationManager,
                jwtEncoder,
                authCookieManager
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:8080", "http://127.0.0.1:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
