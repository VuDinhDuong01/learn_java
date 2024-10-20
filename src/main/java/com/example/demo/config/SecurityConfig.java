package com.example.demo.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        final String[] PUBLIC_ENDPOINTS = { "/auth/login", "/auth/introspect", "/api/v1/users" };

        // permitAll là những router nào match thì k cần authzoiztion còn lại cần.
        httpSecurity.authorizeRequests(
                request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        // .requestMatchers(HttpMethod.GET,"/api/v1/users")
                        // // có thể dùng 1 trong 2 cách
                        // // đây là phân quyên thèo url
                        // .hasRole(Role.ADMIN.name())
                        // .hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated());

        //
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfig -> jwtConfig.decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthentioncationEntryPoint())
                );
        // tắt cors
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    //
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                "3YjW35WxwwJHXS7NiQsNrdeilhj2wyqp5qcHmJlOeGLrVOoms6wcqvqP161tF2SC".getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    };

    // khi đánh dấu là Bean thì sẽ đưa biến passwordEncoder vào application context
    // và có thể gọi ở mọi nơi trong ứng dụng.
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // config scope
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE ");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
    public CorsFilter corsFilter(){

        CorsConfiguration configuration= new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("https...");
        configuration.addAllowedMethod("*");

        // cái này dùng để muốn khai báo cors cho những url nào.
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource= new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
