package com.example.CakeShopManagement.config;


import com.example.CakeShopManagement.filters.JwtRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final JwtRequestFilter authFilter;

    public WebSecurityConfiguration(JwtRequestFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {

        org.springframework.web.cors.CorsConfiguration configuration =
                new org.springframework.web.cors.CorsConfiguration();

        configuration.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.List.of("*"));
        configuration.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf((csrf)->csrf.disable())
//                .cors(cors -> {})
//                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
//                .authorizeHttpRequests((auth)->auth
//                        .requestMatchers("/authenticate","/sign-up","/order/**","/api/public/**","/api/customer/**","/ws/**").permitAll()
//
////                        Admin only
//                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN","EMPLOYEE")
//
////                        Admin + Employee
//                        .requestMatchers("/api/employee/**").hasAnyRole("ADMIN","EMPLOYEE")
////                        .requestMatchers("/customer/**").hasAnyRole("ADMIN","EMPLOYEE")
//
////                        any logged user
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement((session)->session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf)->csrf.disable())
                .cors(cors -> {})
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/authenticate","/sign-up","/order/**","/api/public/**","/api/customer/**","/ws/**").permitAll()

                        // Remove the hardcoded .hasAnyRole("ADMIN","EMPLOYEE")
                        // Allow any authenticated user to access these, relying on the
                        // frontend permissions or method-level @PreAuthorize for security
                        .requestMatchers("/api/admin/**").authenticated()
                        .requestMatchers("/api/employee/**").authenticated()

                        // any logged user
                        .anyRequest().authenticated()
                )
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf((csrf) -> csrf.disable())
//                .cors(cors -> {})
//                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/authenticate", "/sign-up", "/order/**", "/api/public/**", "/api/customer/**", "/ws/**").permitAll()
//
//                        // READ-ONLY: Allow Employees to view data (GET requests)
//                        .requestMatchers(HttpMethod.GET, "/api/admin/**").hasAnyRole("ADMIN", "EMPLOYEE")
//
//                        // WRITE/MODIFY: Restrict CREATE, EDIT, and DELETE requests strictly to ADMIN
//                        .requestMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/admin/**").hasRole("ADMIN")
//
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
